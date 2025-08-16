package seg.work.carog.server.maintenance.repository;

import java.util.Optional;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

public interface MaintenanceCostInfoRepository extends BaseRepository<MaintenanceCostInfoEntity> {

    Optional<Slice<MaintenanceCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);
}
