package seg.work.carog.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seg.work.carog.server.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String email;
    private String nickname;

    public UserInfoResponse(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getNickname();
    }
}
