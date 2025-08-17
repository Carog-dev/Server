package seg.work.carog.server.common.config.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String WRITE_KEY = "WRITE";
    public static final String READ_KEY_PREFIX = "READ_";

    private final boolean writeOnly;
    private final List<String> readKeys;
    private final int readSize;
    private final AtomicInteger readIdx = new AtomicInteger(-1);

    public RoutingDataSource(DataSource writeDataSource, List<DataSource> readDataSources) {
        if (writeDataSource == null) {
            log.error("writeDataSource 미 존재");
            throw new BaseException(Message.FAIL);
        }

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(WRITE_KEY, new LazyConnectionDataSourceProxy(writeDataSource));

        if (!CollectionUtils.isEmpty(readDataSources)) {
            this.readKeys = new ArrayList<>(readDataSources.size());

            for (int i = 0; i < readDataSources.size(); i++) {
                String key = READ_KEY_PREFIX + i;
                this.readKeys.add(key);
                targetDataSources.put(key, new LazyConnectionDataSourceProxy(readDataSources.get(i)));
            }

            this.readSize = this.readKeys.size();
            this.writeOnly = false;
        } else {
            this.readKeys = new ArrayList<>();
            this.readSize = 0;
            this.writeOnly = true;
            log.warn("readDataSource가 제공되지 않았습니다. 모든 작업은 writeDataSource를 사용합니다.");
        }

        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(targetDataSources.get(WRITE_KEY));

        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String key = WRITE_KEY;

        if (!writeOnly && TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            key = getReadDataSourceKey();
            log.info("현재 트랜잭션은 읽기 전용입니다. readDataSource로 라우팅: [{}]", key);
        } else {
            log.info("현재 트랜잭션은 읽기 전용이 아니거나 readDataSource가 구성되지 않았습니다. writeDataSource 라우팅: [{}]", key);
        }

        return key;
    }

    private String getReadDataSourceKey() {
        if (readSize == 0) {
            log.warn("readDataSource가 구성되지 않아 writeDataSource로 대체합니다.");
            return WRITE_KEY;
        }

        /*
        readIdx.updateAndGet() : AtomicInteger를 사용하여 현재 값을 증가시키고, 결과를 readSize로 나눈 나머지를 계산하여 인덱스가 readKeys의 범위 내에서 라운드 로빈 방식으로 순환
        EX) readSize가 3일 때, 0 -> 1 -> 2 -> 0 -> 1 ...
         */
        return readKeys.get(readIdx.updateAndGet(v -> (v + 1) % readSize));
    }
}
