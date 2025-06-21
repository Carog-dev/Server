package seg.work.carog.server.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.entity.BaseEntity;
import seg.work.carog.server.user.dto.UserInfoDTO;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_info")
public class UserInfoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_id", nullable = false, unique = true)
    private String oauthId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    public UserInfoEntity(UserInfoDTO userInfoDTO) {
        this.id = userInfoDTO.getId();
        this.oauthId = userInfoDTO.getOauthId();
        this.name = userInfoDTO.getName();
        this.email = userInfoDTO.getEmail();
    }
}