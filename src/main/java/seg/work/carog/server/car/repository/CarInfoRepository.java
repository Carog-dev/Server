package seg.work.carog.server.car.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.common.repository.BaseRepository;

public interface CarInfoRepository extends BaseRepository<CarInfoEntity> {

    Optional<CarInfoEntity> findByUserIdAndRepresentAndDeleteYn(@NotNull Long userId, Boolean represent, String deleteYn);

    Optional<List<CarInfoEntity>> findByUserIdAndDeleteYn(Long userId, String deleteYn);

    Optional<List<CarInfoEntity>> findByUserIdAndDeleteYnOrderByRepresentDescCreatedAt(Long userId, String deleteYn);

    Optional<CarInfoEntity> findByUserIdAndIdAndDeleteYn(Long userId, Long id, String deleteYn);

    Optional<CarInfoEntity> findByUserIdAndIdAndRepresentAndDeleteYn(@NotNull Long userId, Long id, Boolean represent, String deleteYn);

    boolean existsByUserIdAndNumberAndDeleteYn(Long userId, String number, String deleteYn);
}
