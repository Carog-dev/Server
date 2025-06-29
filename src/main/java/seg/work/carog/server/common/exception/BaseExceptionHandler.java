package seg.work.carog.server.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.constant.Message;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleBaseException(BaseException be) {
        log.error("BaseException occurred: {}", be.getMessage(), be);
        return new ResponseEntity<>(new BaseResponse(be.getCode(), be.getMessage()), HttpStatus.valueOf(be.getCode()));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error("Exception occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(new BaseResponse(Message.INTERNAL_SERVER_ERROR.getCode(), Message.INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException re) {
        log.error("runtimeException occurred: {}", re.getMessage(), re);
        return new ResponseEntity<>(new BaseResponse(Message.INTERNAL_SERVER_ERROR.getCode(), Message.INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    protected ResponseEntity<BaseResponse> handleNoResourceFoundExceptionException(NoResourceFoundException nfe) {
        log.error("NoResourceFoundException occurred: {}", nfe.getMessage(), nfe);
        return new ResponseEntity<>(new BaseResponse(Message.NOT_FOUND.getCode(), Message.NOT_FOUND.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodNotAllowedException.class})
    protected ResponseEntity<BaseResponse> handleMethodNotAllowedException(MethodNotAllowedException mnae) {
        log.error("MethodNotAllowedException occurred: {}", mnae.getMessage(), mnae);
        return new ResponseEntity<>(new BaseResponse(Message.METHOD_NOT_ALLOW.getCode(), Message.METHOD_NOT_ALLOW.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<BaseResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException hrmse) {
        log.error("HttpRequestMethodNotSupportedException occurred: {}", hrmse.getMessage(), hrmse);
        return new ResponseEntity<>(new BaseResponse(Message.METHOD_NOT_SUPPORT.getCode(), Message.METHOD_NOT_SUPPORT.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<BaseResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException msrpe) {
        log.error("MissingServletRequestParameterException occurred: {}", msrpe.getMessage(), msrpe);
        return new ResponseEntity<>(new BaseResponse(Message.BAD_REQUEST.getCode(), Message.BAD_REQUEST.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    protected ResponseEntity<BaseResponse> handleMissingServletRequestPartException(MissingServletRequestPartException msrpe) {
        log.error("MissingServletRequestPartException occurred: {}", msrpe.getMessage(), msrpe);
        return new ResponseEntity<>(new BaseResponse(Message.BAD_REQUEST.getCode(), Message.BAD_REQUEST.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<BaseResponse> handleBadCredentialsException(BadCredentialsException bce) {
        log.error("BadCredentialsException occurred: {}", bce.getMessage(), bce);
        return new ResponseEntity<>(new BaseResponse(Message.UNAUTHORIZED.getCode(), Message.UNAUTHORIZED.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<BaseResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException hmnre) {
        log.error("HttpMessageNotReadableException occurred: {}", hmnre.getMessage(), hmnre);
        return new ResponseEntity<>(new BaseResponse(Message.BAD_REQUEST.getCode(), Message.BAD_REQUEST.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve) {
        log.error("MethodArgumentNotValidException occurred: {}", manve.getMessage(), manve);
        return new ResponseEntity<>(new BaseResponse(Message.BAD_REQUEST.getCode(), Message.BAD_REQUEST.getMessage()), HttpStatus.BAD_REQUEST);
    }
}