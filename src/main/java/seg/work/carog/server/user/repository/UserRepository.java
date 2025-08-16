package seg.work.carog.server.user.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.user.entity.UserEntity;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {

    Optional<UserEntity> findByKeyAndDeleteYn(String key, String deleteYn);

    Optional<UserEntity> findByKakaoIdAndDeleteYn(String kakaoId, String deleteYn);

    Optional<UserEntity> findByEmailAndDeleteYn(String email, String deleteYn);
}