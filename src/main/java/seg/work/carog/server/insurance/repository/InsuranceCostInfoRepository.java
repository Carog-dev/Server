package seg.work.carog.server.insurance.repository;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;

public interface InsuranceCostInfoRepository extends BaseRepository<InsuranceCostInfoEntity> {

    Optional<Slice<InsuranceCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn, Pageable pageable);
}