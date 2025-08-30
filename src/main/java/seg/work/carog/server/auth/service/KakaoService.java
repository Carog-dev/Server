package seg.work.carog.server.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import seg.work.carog.server.auth.dto.KakaoUserInfo;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final WebClient webClient = WebClient.builder().build();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public String getAccessToken(String authorizationCode) {
        try {
            KakaoTokenResponse response = webClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("code", authorizationCode))
                    .retrieve()
                    .bodyToMono(KakaoTokenResponse.class)
                    .block();

            if (response != null) {
                return response.getAccessToken();
            } else {
                log.error("Kakao access token 발급 오류");
                throw new RuntimeException("Failed to get access token from Kakao");
            }
        } catch (Exception e) {
            log.error("ERROR: ", e);
            throw new BaseException(Message.KAKAO_ACCESS_TOKEN_ERROR);
        }
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        try {
            return webClient.get()
                    .uri(KAKAO_USER_INFO_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoUserInfo.class)
                    .block();
        } catch (Exception e) {
            log.error("Kakao 사용자 정보 조회 오류", e);
            throw new BaseException(Message.KAKAO_GET_USER_INFO);
        }
    }

    @Getter
    @Setter
    private static class KakaoTokenResponse {

        private String access_token;
        private String token_type;
        private String refresh_token;
        private Integer expires_in;
        private String scope;
        private Integer refresh_token_expires_in;

        public String getAccessToken() {
            return access_token;
        }

        public String getTokenType() {
            return token_type;
        }

        public String getRefreshToken() {
            return refresh_token;
        }

        public Integer getExpiresIn() {
            return expires_in;
        }

        public Integer getRefreshTokenExpiresIn() {
            return refresh_token_expires_in;
        }
    }
}