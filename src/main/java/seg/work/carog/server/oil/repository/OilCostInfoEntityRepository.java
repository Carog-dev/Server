package seg.work.carog.server.oil.repository;

import java.util.Optional;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;

public interface OilCostInfoEntityRepository extends BaseRepository<OilCostInfoEntity> {

    Optional<Slice<OilCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);
}