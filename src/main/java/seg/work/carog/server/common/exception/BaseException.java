package seg.work.carog.server.common.exception;

import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import seg.work.carog.server.common.constant.Message;

@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 588105335815890991L;

    private final String code;
    private final HttpStatus httpStatus;

    public BaseException(Message message) {
        super(message.getMessage());
        this.code = message.getCode();
        this.httpStatus = message.getHttpStatus();
    }
}
