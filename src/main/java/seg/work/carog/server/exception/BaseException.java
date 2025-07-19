package seg.work.carog.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import seg.work.carog.server.common.constant.Message;

@Getter
public class BaseException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    public BaseException(Message message) {
        super(message.getMessage());
        this.code = message.getCode();
        this.httpStatus = message.getHttpStatus();
    }
}
