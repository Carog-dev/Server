package seg.work.carog.server.common.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.dto.BaseApiResponse;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.common.service.BlacklistTokenService;
import seg.work.carog.server.common.service.RefreshTokenService;
import seg.work.carog.server.common.util.JwtUtil;
import seg.work.carog.server.common.util.ObjectUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final BlacklistTokenService blacklistTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        MDC.put("traceId", UUID.randomUUID().toString());

        String token = resolveToken(request);

        try {
            if (StringUtils.hasText(token)) {
                try {
                    if (jwtUtil.validateToken(token)) {
                        Authentication authentication = jwtUtil.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (ExpiredJwtException e) {
                    String refreshToken = jwtUtil.getRefreshTokenFromToken(token);
                    if (StringUtils.hasText(refreshToken)) {
                        try {
                            String newAccessToken = jwtUtil.refreshAccessToken(refreshToken);
                            refreshTokenService.renameRefreshToken(token, newAccessToken);

                            Authentication authentication = jwtUtil.getAuthentication(newAccessToken);
                            SecurityContextHolder.getContext().setAuthentication(authentication);

                            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
                            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");

                            log.info("Access token has expired. renewed with a refresh token.");
                        } catch (Exception refreshException) {
                            log.error("Refresh 토큰으로 갱신 실패: {}", refreshException.getMessage());
                            handleRefreshTokenException(refreshException, response);
                            return;
                        }
                    } else {
                        throw e;
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(e, request, response, token);
        } finally {
            MDC.clear();
            SecurityContextHolder.clearContext();
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void handleException(Exception e, HttpServletRequest request, HttpServletResponse response, String token) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            if (request.getRequestURI().endsWith("/logout/kakao")) {
                BaseApiResponse<?> baseApiResponse = BaseApiResponse.success();

                if (!token.isBlank() && !token.equals("null")) {
                    Message message = Message.EXPIRED_TOKEN_RE_LOGIN;

                    response.setStatus(message.getHttpStatus().value());
                    if (blacklistTokenService.isBlockedToken(token)) {
                        baseApiResponse = BaseApiResponse.error(message);
                    } else {
                        response.setStatus(Message.SUCCESS.getHttpStatus().value());
                        blacklistTokenService.addTokenToBlacklist(token);
                    }
                }

                response.getWriter().write(ObjectUtil.convertObjectToString(baseApiResponse));
            } else {
                Object data = null;
                Message message;

                if (e instanceof ExpiredJwtException) {
                    message = Message.EXPIRED_TOKEN;
                } else if (e instanceof MalformedJwtException || e instanceof UnsupportedJwtException || e instanceof SignatureException || e instanceof IllegalArgumentException) {
                    message = Message.INVALID_TOKEN;
                } else if (e instanceof BaseException) {
                    message = Message.fromCode(((BaseException) e).getCode());
                } else {
                    message = Message.FAIL;
                }

                response.setStatus(message.getHttpStatus().value());
                response.getWriter().write(ObjectUtil.convertObjectToString(BaseApiResponse.error(message, data)));
            }
        } catch (IOException ioe) {
            log.error("Error while sending JWT Token", ioe);
        }
    }

    private void handleRefreshTokenException(Exception e, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Message message;

        if (e instanceof ExpiredJwtException) {
            message = Message.EXPIRED_TOKEN_RE_LOGIN;
        } else if (e instanceof MalformedJwtException || e instanceof UnsupportedJwtException || e instanceof SignatureException || e instanceof IllegalArgumentException) {
            message = Message.INVALID_TOKEN;
        } else if (e instanceof BaseException) {
            message = Message.fromCode(((BaseException) e).getCode());
        } else {
            message = Message.FAIL;
        }

        response.setStatus(message.getHttpStatus().value());
        response.getWriter().write(ObjectUtil.convertObjectToString(BaseApiResponse.error(message, null)));
    }
}