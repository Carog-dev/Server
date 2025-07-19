package seg.work.carog.server.config.data;

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
import seg.work.carog.server.exception.BaseException;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String WRITE_KEY = "write";
    public static final String READ_KEY_PREFIX = "read_";

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
            this.writeOnly = false; // readDataSource 존재
        } else {
            this.readKeys = new ArrayList<>();
            this.readSize = 0;
            this.writeOnly = true; // readDataSource 미존재
            log.warn("readDataSource가 제공되지 않았습니다. 모든 작업은 writeDataSource를 사용합니다.");
        }

        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(targetDataSources.get(WRITE_KEY));

        // AbstractRoutingDataSource의 초기화 보장
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String key = WRITE_KEY; // 기본값은 쓰기

        // 현재 트랜잭션이 읽기 전용이고 readDataSource를 사용할 수 있는지 확인
        if (!writeOnly && TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            key = getReadDataSourceKey();
            log.debug("현재 트랜잭션은 읽기 전용입니다. readDataSource로 라우팅: [{}]", key);
        } else {
            // 읽기 전용이 아닌 트랜잭션이거나 readDataSource가 구성되지 않은 경우
            log.debug("현재 트랜잭션은 읽기 전용이 아니거나 readDataSource가 구성되지 않았습니다. writeDataSource 라우팅: [{}]", key);
        }

        return key;
    }

    private String getReadDataSourceKey() {
        if (readSize == 0) {
            // writeOnly가 true인 경우 발생해서는 안 되지만, 보호 조치로 포함
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
