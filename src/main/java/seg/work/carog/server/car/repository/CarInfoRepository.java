package seg.work.carog.server.car.repository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import seg.work.carog.server.car.entity.CarInfoEntity;

public interface CarInfoRepository extends JpaRepository<CarInfoEntity, Long> {

    Optional<CarInfoEntity> findByUserIdAndRepresent(@NotNull Long userId, Boolean represent);

    Optional<Slice<CarInfoEntity>> findByUserId(Long userId);

    Optional<CarInfoEntity> findByUserIdAndId(Long userId, Long id);

    boolean existsByUserIdAndNumber(Long userId, String number);
}
