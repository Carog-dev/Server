package seg.work.carog.server.maintenance.repository;

import java.util.Optional;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

public interface MaintenanceCostInfoRepository extends JpaRepository<MaintenanceCostInfoEntity, Long> {

//    Optional<MaintenanceCostInfoEntity> findById(Long id);
    Optional<Slice<MaintenanceCostInfoEntity>> findByCarInfoId(Long carInfoId);
}
