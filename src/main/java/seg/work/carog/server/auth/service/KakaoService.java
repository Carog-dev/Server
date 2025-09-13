package seg.work.carog.server.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import seg.work.carog.server.auth.dto.KakaoLoginRequest;
import seg.work.carog.server.auth.dto.KakaoTokenResponse;
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

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public String getAccessToken(KakaoLoginRequest request) {
        try {
            KakaoTokenResponse response = webClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", request.getRedirectUri())
                        .with("code", request.getCode()))
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
}