package seg.work.carog.server.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.KakaoUserInfo;
import seg.work.carog.server.auth.dto.LoginResponse;
import seg.work.carog.server.auth.entity.User;
import seg.work.carog.server.auth.repository.UserRepository;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.exception.BaseException;
import seg.work.carog.server.util.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

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
            User user = findOrCreateUser(kakaoUserInfo);

            // 4. JWT 토큰 생성
            String jwtToken = jwtUtil.generateToken(user.getId().toString(), user.getEmail(), user.getRole().name());

            return LoginResponse.builder()
                    .token(jwtToken)
                    .tokenType("Bearer")
                    .expiresDate(jwtUtil.parserToken(jwtToken).getExpiration())
                    .user(LoginResponse.UserInfo.builder()
                            .email(user.getEmail())
                            .nickname(user.getNickname())
                            .profileImageUrl(user.getProfileImageUrl())
                            .build())
                    .build();

        } catch (Exception e) {
            throw new BaseException(Message.LOGIN_FAIL);
        }
    }

    private User findOrCreateUser(KakaoUserInfo kakaoUserInfo) {
        return userRepository.findByKakaoId(kakaoUserInfo.getId().toString()).orElseGet(() -> createNewUser(kakaoUserInfo));
    }

    private User createNewUser(KakaoUserInfo kakaoUserInfo) {
        String email = extractEmail(kakaoUserInfo);
        String nickname = extractNickname(kakaoUserInfo);
        String profileImageUrl = extractProfileImageUrl(kakaoUserInfo);

        User newUser = User.builder()
                .kakaoId(kakaoUserInfo.getId().toString())
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(User.UserRole.USER)
                .build();

        return userRepository.save(newUser);
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

        log.warn("카카오 닉네임 추출 실패");
        return null;
    }
}