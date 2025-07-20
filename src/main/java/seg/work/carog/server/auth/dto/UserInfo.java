package seg.work.carog.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.auth.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long id;
    private String key;
    private String email;
    private String accessToken;

    public static UserInfo toDto(User user, String accessToken) {
        return UserInfo.builder()
                .key(user.getKey())
                .email(user.getEmail())
                .accessToken(accessToken)
                .build();
    }
}
