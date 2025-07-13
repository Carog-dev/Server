package seg.work.carog.server.config.security;

import jakarta.servlet.http.Cookie;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import seg.work.carog.server.common.util.JwtUtil;
import seg.work.carog.server.config.jwt.JwtAuthenticationFilter;
import seg.work.carog.server.user.dto.UserInfoDTO;
import seg.work.carog.server.user.entity.UserInfoEntity;
import seg.work.carog.server.user.repository.UserInfoRepository;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserInfoRepository userInfoRepository;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/oauth2/**",
                                "/login/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .userInfoEndpoint(endpoint -> endpoint.userService(oauth2UserService()))
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedOrigins(List.of("http://localhost:3003"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public DefaultOAuth2UserService oauth2UserService() {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oauth2User = super.loadUser(userRequest);
                Map<String, Object> attributes = oauth2User.getAttributes();
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

                String oauthId = attributes.get("id").toString();
                String email = (String) kakaoAccount.get("email");
                String name = (String) profile.get("nickname");

                UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                        .oauthId(oauthId)
                        .email(email)
                        .name(name)
                        .build();

                Optional<UserInfoEntity> optionalUserInfoEntity = userInfoRepository.findByOauthId(userInfoDTO.getOauthId());
                userInfoRepository.save(optionalUserInfoEntity.orElseGet(() -> new UserInfoEntity(userInfoDTO)));
                return oauth2User;
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String oauthId = oauth2User.getAttribute("id").toString();
            String accessToken = jwtUtil.generateToken(oauthId);

            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge((int) (jwtUtil.parserToken(accessToken).getExpiration().getTime() / 1000));
            response.addCookie(accessTokenCookie);

            response.sendRedirect("http://localhost:3003/login");
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            log.error("Kakao OAuth2 authentication failed: {}", exception.getMessage(), exception);

            String errorMessage = "Kakao 로그인에 실패했습니다. 다시 시도해 주세요.";
            if (exception.getMessage().contains("access_denied")) {
                errorMessage = "Kakao 로그인 동의를 취소했습니다.";
            } else if (exception.getMessage().contains("invalid_token")) {
                errorMessage = "유효하지 않은 토큰입니다. 다시 로그인해 주세요.";
            }

            String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
            String redirectUrl = "http://localhost:3003/callback?error=" + encodedErrorMessage;
            response.sendRedirect(redirectUrl);
        };
    }
}