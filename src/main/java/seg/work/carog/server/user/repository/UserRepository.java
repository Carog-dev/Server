package seg.work.carog.server.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seg.work.carog.server.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKey(String key);
    Optional<UserEntity> findByKakaoId(String kakaoId);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByKakaoId(String kakaoId);
}