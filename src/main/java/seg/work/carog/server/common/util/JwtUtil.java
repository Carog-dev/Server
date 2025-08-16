package seg.work.carog.server.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.config.security.UserDetailService;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.common.service.RefreshTokenService;
import seg.work.carog.server.user.entity.UserEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final UserDetailService userDetailService;
    private final RefreshTokenService refreshTokenService;

    public String generateToken(UserEntity userEntity) {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());

        long token_expiration_millisecond;
        long redis_expiration_hour;
        switch (userEntity.getRole()) {
            // USER 2시간
            case USER -> {
                token_expiration_millisecond = 1000 * 60 * 60 * 2;
                redis_expiration_hour = 2;
            }
            // DEVELOPER 8시간
            case DEVELOPER -> {
                token_expiration_millisecond = 1000 * 60 * 60 * 8;
                redis_expiration_hour = 8;
            }
            // ADMIN 12시간
            case ADMIN -> {
                token_expiration_millisecond = 1000 * 60 * 60 * 12;
                redis_expiration_hour = 12;
            }
            default -> throw new BaseException(Message.USER_NOT_FOUND);
        }

        RedisUtil.setWithExpiryHour(userEntity.getKey(), userEntity.getId().toString(), redis_expiration_hour);

        return Jwts.builder()
                .subject(userEntity.getKey())
                .claim("type", "access")
                .claim("email", userEntity.getEmail())
                .claim("auth", userEntity.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + token_expiration_millisecond))
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Map<String, Object> claims = this.parserToken(accessToken);

        if (claims.get("auth") == null) {
            throw new BaseException(Message.UNAUTHORIZED);
        } else if (!"access".equals(claims.get("type"))) {
            throw new BaseException(Message.UNSUPPORTED_TOKEN);
        }

        Collection<GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .filter(role -> !role.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserEntity userEntity = userDetailService.loadUserInfoBySubject(claims.get("sub").toString());
        return new UsernamePasswordAuthenticationToken(TokenUserInfo.toDto(userEntity, accessToken), "", authorities);
    }

    public Claims parserToken(String token) {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserKeyFromToken(String token) {
        return parserToken(token).getSubject();
    }

    public String getEmailFromToken(String token) {
        return parserToken(token).get("email", String.class);
    }

    public String getRoleFromToken(String token) {
        return parserToken(token).get("auth", String.class);
    }

    public String getTypeFromToken(String token) {
        return parserToken(token).get("type", String.class);
    }

    public boolean validateToken(String token) {
        try {
            parserToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT access token: {}", e.getMessage());
            if (e instanceof ExpiredJwtException) {
                throw e;
            } else if (e instanceof MalformedJwtException || e instanceof UnsupportedJwtException || e instanceof SignatureException || e instanceof IllegalArgumentException) {
                throw new BaseException(Message.INVALID_TOKEN);
            } else {
                throw new BaseException(Message.FAIL);
            }
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            parserToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT refresh token: {}", e.getMessage());
            if (e instanceof ExpiredJwtException) {
                throw new BaseException(Message.EXPIRED_TOKEN);
            } else if (e instanceof MalformedJwtException || e instanceof UnsupportedJwtException || e instanceof SignatureException || e instanceof IllegalArgumentException) {
                throw new BaseException(Message.INVALID_TOKEN);
            } else {
                throw new BaseException(Message.FAIL);
            }
        }
    }

    public String getRefreshTokenFromToken(String token) {
        return refreshTokenService.getRefreshToken(token);
    }

    public String generateRefreshToken(UserEntity userEntity) {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());

        long expiration_millisecond = 1000L * 60 * 60 * 24 * 30 * 6;

        return Jwts.builder()
                .subject(userEntity.getKey())
                .claim("type", "refresh")
                .claim("email", userEntity.getEmail())
                .claim("auth", userEntity.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration_millisecond))
                .signWith(secretKey)
                .compact();
    }

    public String refreshAccessToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new BaseException(Message.INVALID_TOKEN);
        } else if (!"refresh".equals(getTypeFromToken(refreshToken))) {
            throw new BaseException(Message.UNSUPPORTED_TOKEN);
        }

        String userKey = getUserKeyFromToken(refreshToken);
        UserEntity userEntity = userDetailService.loadUserInfoByKey(userKey);
        if (userEntity == null) {
            throw new BaseException(Message.USER_NOT_FOUND);
        }

        return generateToken(userEntity);
    }
}