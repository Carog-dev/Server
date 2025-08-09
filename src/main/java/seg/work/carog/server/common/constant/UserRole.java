package seg.work.carog.server.common.constant;

import lombok.Getter;

@Getter
public enum UserRole {
    TEMP_USER,
    USER,
    DEVELOPER,
    ADMIN,
    ;

    public String getRole() {
        return "ROLE_" + this.name();
    }
}
