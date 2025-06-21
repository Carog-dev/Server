package seg.work.carog.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seg.work.carog.server.common.dto.BaseDTO;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO extends BaseDTO {

    private Long id;
    private String oauthId;
    private String name;
    private String email;
}