package seg.work.carog.server.accident.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;
import seg.work.carog.server.common.repository.BaseRepository;

public interface AccidentCostInfoRepository extends BaseRepository<AccidentCostInfoEntity> {

    Optional<List<AccidentCostInfoEntity>> findAllByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);

    Optional<Slice<AccidentCostInfoEntity>> findByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn, Pageable pageable);
}