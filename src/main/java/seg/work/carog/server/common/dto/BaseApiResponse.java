package seg.work.carog.server.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seg.work.carog.server.common.constant.Message;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseApiResponse<T> {

    private Boolean success = false;
    private String code = Message.SUCCESS.getCode();
    private String message = Message.SUCCESS.getMessage();
    private T data = null;

    public static <T> BaseApiResponse<T> success() {
        return new BaseApiResponse<>(true, Message.SUCCESS.getCode(), Message.SUCCESS.getMessage(), null);
    }

    public static <T> BaseApiResponse<T> success(T data) {
        return new BaseApiResponse<>(true, Message.SUCCESS.getCode(), Message.SUCCESS.getMessage(), data);
    }

    public static <T> BaseApiResponse<T> error(Message message) {
        return new BaseApiResponse<>(false, message.getCode(), message.getMessage(), null);
    }

    public static <T> BaseApiResponse<T> error(Message message, T data) {
        return new BaseApiResponse<>(false, message.getCode(), message.getMessage(), data);
    }
}
