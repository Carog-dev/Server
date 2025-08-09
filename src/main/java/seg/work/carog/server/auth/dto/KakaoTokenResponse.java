package seg.work.carog.server.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoTokenResponse {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String scope;
    private Integer refresh_token_expires_in;

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public Integer getExpiresIn() {
        return expires_in;
    }

    public Integer getRefreshTokenExpiresIn() {
        return refresh_token_expires_in;
    }
}