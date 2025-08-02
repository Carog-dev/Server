package seg.work.carog.server.accident.repository;

import java.util.Optional;
import org.springframework.data.domain.Slice;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;
import seg.work.carog.server.common.repository.BaseRepository;

public interface AccidentCostInfoRepository extends BaseRepository<AccidentCostInfoEntity> {

    Optional<Slice<AccidentCostInfoEntity>> findByCarInfoId(Long carInfoId);
}