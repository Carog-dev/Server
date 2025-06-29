package seg.work.carog.server.common.exception;

import java.io.Serial;
import lombok.Getter;
import seg.work.carog.server.constant.Message;

@Getter
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String code;

    public BaseException(Message message) {
        super(message.getMessage());
        this.code = message.getCode();
    }
}
