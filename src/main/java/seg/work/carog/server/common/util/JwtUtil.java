package seg.work.carog.server.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
import seg.work.carog.server.user.entity.UserEntity;
import seg.work.carog.server.common.config.security.UserDetailService;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final UserDetailService userDetailService;

    public String generateToken(UserEntity userEntity) {
        // 12시간
        long EXPIRATION_TIME = 1000 * 60 * 60 * 12;

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());

        RedisUtil.setWithExpiryHour(userEntity.getKey(), userEntity.getId().toString(), 4);

        return Jwts.builder()
                .subject(userEntity.getKey())
                .claim("email", userEntity.getEmail())
                .claim("auth", userEntity.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Map<String, Object> claims = this.parserToken(accessToken);

        if (claims.get("auth") == null) {
            throw new BaseException(Message.UNAUTHORIZED);
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

    public boolean validateToken(String token) {
        try {
            parserToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return parserToken(token).getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }
}