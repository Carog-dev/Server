package seg.work.carog.server.common.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.entity.User;
import seg.work.carog.server.auth.repository.UserRepository;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new BaseException(Message.USER_NOT_FOUND));
    }

    public User loadUserInfoBySubject(String subject) {
        return userRepository.findByKey(subject).orElseThrow(() -> new BaseException(Message.USER_NOT_FOUND));
    }
}
