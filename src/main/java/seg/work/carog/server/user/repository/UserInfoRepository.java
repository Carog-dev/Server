package seg.work.carog.server.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import seg.work.carog.server.user.entity.UserInfoEntity;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByOauthId(String oauthId);
}