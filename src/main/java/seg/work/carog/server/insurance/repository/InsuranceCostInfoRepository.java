package seg.work.carog.server.insurance.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;

public interface InsuranceCostInfoRepository extends BaseRepository<InsuranceCostInfoEntity> {

    Optional<List<InsuranceCostInfoEntity>> findAllByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);

    Optional<Slice<InsuranceCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn, Pageable pageable);
}