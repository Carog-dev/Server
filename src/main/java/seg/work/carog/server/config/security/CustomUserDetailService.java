package seg.work.carog.server.config.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seg.work.carog.server.user.entity.UserInfoEntity;
import seg.work.carog.server.user.repository.UserInfoRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    public UserInfoEntity loadUserByOauthId(String oauthId) throws UsernameNotFoundException {
        return userInfoRepository.findByOauthId(oauthId).orElseThrow(() -> new UsernameNotFoundException("User not found with oauthId: " + oauthId));
    }
}
