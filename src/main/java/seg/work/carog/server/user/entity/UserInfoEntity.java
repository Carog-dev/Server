package seg.work.carog.server.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import seg.work.carog.server.common.entity.BaseEntity;
import seg.work.carog.server.constant.SystemRole;
import seg.work.carog.server.user.dto.UserInfoDTO;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_info")
public class UserInfoEntity extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_id", nullable = false, unique = true)
    private String oauthId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private SystemRole role;

    public UserInfoEntity(UserInfoDTO userInfoDTO) {
        this.id = userInfoDTO.getId();
        this.oauthId = userInfoDTO.getOauthId();
        this.name = userInfoDTO.getName();
        this.email = userInfoDTO.getEmail();
        this.role = SystemRole.ROLE_USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}