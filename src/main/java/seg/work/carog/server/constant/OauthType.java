package seg.work.carog.server.constant;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum OauthType {
    KAKAO("kakao"),
    ;

    private final String type;

    OauthType(String type) {
        this.type = type;
    }

    public static OauthType ofType(String type) {
        return Arrays.stream(values())
                .filter(oauthType -> oauthType.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such OauthType"));
    }

}