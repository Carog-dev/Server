package seg.work.carog.server.common.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.dto.BaseResponse;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<BaseResponse<Map<String, String>>> handleRuntimeException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        Message statusInfo = Message.FAIL;
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = null;
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
            }
            String errorMessage = error.getDefaultMessage();
            if (fieldName != null) {
                errors.put(fieldName, errorMessage != null ? errorMessage : "Not Exception Message");
            }
        });
        Message statusInfo = Message.BAD_REQUEST;
        return new ResponseEntity<>(BaseResponse.error(statusInfo, errors), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Message not readable: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        Throwable cause = ex.getCause();

        if (cause instanceof MismatchedInputException mie) {
            String fieldPath = mie.getPath().stream()
                    .filter(Objects::nonNull)
                    .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "")
                    .filter(fieldName -> !fieldName.isEmpty())
                    .reduce((s1, s2) -> s1 + "." + s2)
                    .orElse("unknownField");
            errors.put(fieldPath, "필수값입니다.");
        } else {
            errors.put("parsingError", "요청 본문 파싱에 실패했습니다.");
        }

        Message statusInfo = Message.REQUIRED_PARAMETER_IS_NULL;
        return new ResponseEntity<>(BaseResponse.error(statusInfo, errors), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> badCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials: {}", ex.getMessage());
        Message statusInfo = Message.LOGIN_FAIL;
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class) // Note: NoHandlerFoundException is an older alternative
    public ResponseEntity<BaseResponse<Map<String, String>>> noResourceFoundException(NoResourceFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        Message statusInfo = Message.NOT_EXISTS_REQUEST;
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> methodNotAllowedException(MethodNotAllowedException ex) {
        log.error("Method not allowed: {}", ex.getMessage());
        Message statusInfo = Message.METHOD_NOT_ALLOW;
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HTTP method not supported: {}", ex.getMessage());
        Message statusInfo = Message.METHOD_NOT_SUPPORT;
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    public ResponseEntity<BaseResponse<Map<String, String>>> missingServletRequestParameterException(Exception ex) {
        log.error("Missing request parameter/part: {}", ex.getMessage());
        Message statusInfo = Message.BAD_REQUEST;
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleBaseException(BaseException be) {
        log.error("Custom BaseException: {}", be.getMessage());
        Message statusInfo = Message.fromCode(be.getCode());
        return new ResponseEntity<>(BaseResponse.error(statusInfo), statusInfo.getHttpStatus());
    }
}