package seg.work.carog.server.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private String code;
    private String message;

    public BaseResponse(String code) {
        this.code = code;
        this.message = "성공";
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
