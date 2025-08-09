package seg.work.carog.server.auth.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.KakaoUserInfo;
import seg.work.carog.server.auth.dto.LoginResponse;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.constant.UserRole;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.common.service.AuthTokenService;
import seg.work.carog.server.common.service.BlacklistTokenService;
import seg.work.carog.server.common.service.EmailService;
import seg.work.carog.server.common.util.JwtUtil;
import seg.work.carog.server.user.entity.UserEntity;
import seg.work.carog.server.user.repository.UserRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final BlacklistTokenService blacklistTokenService;
    private final AuthTokenService authTokenService;
    private final EmailService emailService;
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse loginWithKakao(String authorizationCode) {
        try {
            // 1. 카카오로부터 액세스 토큰 획득
            String accessToken = kakaoService.getAccessToken(authorizationCode);

            // 2. 액세스 토큰으로 사용자 정보 조회
            KakaoUserInfo kakaoUserInfo = kakaoService.getUserInfo(accessToken);

            // 3. 사용자 정보로 회원가입 또는 로그인 처리
            UserEntity userEntity = userRepository.findByKakaoId(kakaoUserInfo.getId().toString()).orElseGet(() -> createNewUser(kakaoUserInfo));

            // 4. JWT 토큰 생성
            String jwtToken = jwtUtil.generateToken(userEntity);

            // 5. 인증 메일 발송
            String authToken = UUID.randomUUID().toString();
            authTokenService.addTokenToAuthMap(authToken, userEntity.getId());
            emailService.sendAuthEmail(userEntity.getEmail(), authToken);

            return LoginResponse.builder()
                    .token(jwtToken)
                    .tokenType("Bearer")
                    .expiresDate(jwtUtil.parserToken(jwtToken).getExpiration())
                    .user(LoginResponse.UserInfo.builder()
                            .email(userEntity.getEmail())
                            .nickname(userEntity.getNickname())
                            .profileImageUrl(userEntity.getProfileImageUrl())
                            .build())
                    .build();
        } catch (Exception e) {
            log.error("ERROR : ", e);
            throw new BaseException(Message.LOGIN_FAIL);
        }
    }

    @Transactional
    public void logoutWithKakao(TokenUserInfo tokenUserInfo) {
        if (tokenUserInfo.getAccessToken().isBlank()) {
            throw new BaseException(Message.UNAUTHORIZED);
        } else {
            blacklistTokenService.addTokenToBlacklist(tokenUserInfo.getAccessToken());
        }
    }

    @Transactional
    public void verify(String token) {
        if (authTokenService.checkHashHasKey(token)) {
            Long userId = authTokenService.getAuthMapByAuthToken(token);
            UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new BaseException(Message.USER_NOT_FOUND));
            userEntity.updateUserRole(UserRole.USER);

            userRepository.save(userEntity);
        } else {
            throw new BaseException(Message.AUTH_MAIL_IS_EXPIRED);
        }
    }

    @Transactional
    public void verifyEmailResend(TokenUserInfo tokenUserInfo) {
        String authToken = UUID.randomUUID().toString();
        authTokenService.addTokenToAuthMap(authToken, tokenUserInfo.getId());
        emailService.sendAuthEmail(tokenUserInfo.getEmail(), authToken);
    }

    private UserEntity createNewUser(KakaoUserInfo kakaoUserInfo) {
        String email = extractEmail(kakaoUserInfo);
        String nickname = extractNickname(kakaoUserInfo);
        String profileImageUrl = extractProfileImageUrl(kakaoUserInfo);

        UserEntity newUserEntity = UserEntity.builder()
                .kakaoId(kakaoUserInfo.getId().toString())
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(UserRole.TEMP_USER)
                .build();

        return userRepository.save(newUserEntity);
    }

    private String extractEmail(KakaoUserInfo kakaoUserInfo) {
        if (kakaoUserInfo.getKakaoAccount() != null && kakaoUserInfo.getKakaoAccount().getEmail() != null) {
            return kakaoUserInfo.getKakaoAccount().getEmail();
        } else {
            log.error("카카오 이메일 추출 오류");
            throw new BaseException(Message.FAIL);
        }
    }

    private String extractNickname(KakaoUserInfo kakaoUserInfo) {
        if (kakaoUserInfo.getKakaoAccount() != null && kakaoUserInfo.getKakaoAccount().getProfile() != null && kakaoUserInfo.getKakaoAccount().getProfile().getNickname() != null) {
            return kakaoUserInfo.getKakaoAccount().getProfile().getNickname();
        }

        if (kakaoUserInfo.getProperties() != null && kakaoUserInfo.getProperties().getNickname() != null) {
            return kakaoUserInfo.getProperties().getNickname();
        }

        log.error("카카오 닉네임 추출 오류");
        throw new BaseException(Message.FAIL);
    }

    private String extractProfileImageUrl(KakaoUserInfo kakaoUserInfo) {
        if (kakaoUserInfo.getKakaoAccount() != null && kakaoUserInfo.getKakaoAccount().getProfile() != null && kakaoUserInfo.getKakaoAccount().getProfile().getProfileImageUrl() != null) {
            return kakaoUserInfo.getKakaoAccount().getProfile().getProfileImageUrl();
        }

        if (kakaoUserInfo.getProperties() != null && kakaoUserInfo.getProperties().getProfileImage() != null) {
            return kakaoUserInfo.getProperties().getProfileImage();
        }

        log.warn("카카오 프로필 추출 실패");
        return null;
    }
}