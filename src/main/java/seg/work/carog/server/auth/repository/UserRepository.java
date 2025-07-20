package seg.work.carog.server.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seg.work.carog.server.auth.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKey(String key);
    Optional<User> findByKakaoId(String kakaoId);
    Optional<User> findByEmail(String email);
    boolean existsByKakaoId(String kakaoId);
}