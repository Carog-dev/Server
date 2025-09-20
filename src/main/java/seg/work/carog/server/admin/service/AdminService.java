package seg.work.carog.server.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.constant.UserRole;
import seg.work.carog.server.common.service.AccessTokenService;
import seg.work.carog.server.common.service.BlacklistTokenService;
import seg.work.carog.server.common.util.RedisUtil;
import seg.work.carog.server.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AccessTokenService accessTokenService;
    private final BlacklistTokenService blacklistTokenService;

    @Transactional
    public void revokeToken(TokenUserInfo tokenUserInfo) {
        log.info("revokeToken By : {}", tokenUserInfo.getKey());

        userRepository.findAllByDeleteYn(Constant.FLAG_N).ifPresent(users -> users.stream().filter(user -> user.getRole().equals(UserRole.USER)).forEach(user -> {
            String accessToken = accessTokenService.getAccessToken(user.getKey());
            if (accessToken != null) {
                blacklistTokenService.addTokenToBlacklist(accessToken);
                accessTokenService.deleteAccessToken(user.getKey());
                RedisUtil.delete(user.getKey());
            }
        }));
    }

    @Transactional
    public void revokeToken(TokenUserInfo tokenUserInfo, String userKey) {
        log.info("user {} revokeToken By : {}", userKey, tokenUserInfo.getKey());

        userRepository.findByKeyAndDeleteYn(userKey, Constant.FLAG_N).ifPresent(user -> {
            if (user.getRole().equals(UserRole.USER)) {
                String accessToken = accessTokenService.getAccessToken(user.getKey());
                if (accessToken != null) {
                    blacklistTokenService.addTokenToBlacklist(accessToken);
                    accessTokenService.deleteAccessToken(user.getKey());
                    RedisUtil.delete(user.getKey());
                }
            }
        });
    }
}
