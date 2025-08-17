package seg.work.carog.server.parking.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.parking.entity.ParkingCostInfoEntity;

public interface ParkingCostInfoRepository extends BaseRepository<ParkingCostInfoEntity> {

    Optional<List<ParkingCostInfoEntity>> findAllByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);

    Optional<Slice<ParkingCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn, Pageable pageable);
}