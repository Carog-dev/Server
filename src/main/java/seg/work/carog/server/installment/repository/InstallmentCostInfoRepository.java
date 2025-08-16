package seg.work.carog.server.installment.repository;

import java.util.Optional;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;

public interface InstallmentCostInfoRepository extends BaseRepository<InstallmentCostInfoEntity> {

    Optional<Slice<InstallmentCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);
}