package seg.work.carog.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.user.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenUserInfo {

    private Long id;
    private String key;
    private String email;
    private String accessToken;

    public static TokenUserInfo toDto(UserEntity userEntity, String accessToken) {
        return TokenUserInfo.builder()
                .key(userEntity.getKey())
                .email(userEntity.getEmail())
                .accessToken(accessToken)
                .build();
    }
}
