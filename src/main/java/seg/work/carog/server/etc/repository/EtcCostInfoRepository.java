package seg.work.carog.server.etc.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.etc.entity.EtcCostInfoEntity;

public interface EtcCostInfoRepository extends BaseRepository<EtcCostInfoEntity> {

    Optional<List<EtcCostInfoEntity>> findAllByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);

    Optional<Slice<EtcCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn, Pageable pageable);
}