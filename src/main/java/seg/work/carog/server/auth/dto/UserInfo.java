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
public class UserInfo {

    private Long id;
    private String key;
    private String email;
    private String accessToken;

    public static UserInfo toDto(UserEntity userEntity, String accessToken) {
        return UserInfo.builder()
                .key(userEntity.getKey())
                .email(userEntity.getEmail())
                .accessToken(accessToken)
                .build();
    }
}
