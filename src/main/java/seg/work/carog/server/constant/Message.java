package seg.work.carog.server.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {

    SUCCESS_OK("200", "성공"),

    NO_CONTENT("204", "컨텐츠가 없습니다."),

    BAD_REQUEST("400", "잘못된 요청입니다."),
    INVALID_INPUT("400", "잘못된 입력입니다."),
    INVALID_USER("400", "유효하지 않은 사용자입니다."),
    INVALID_PARAMETER("400", "유효하지 않은 파라미터입니다."),
    INVALID_REQUEST("400", "잘못된 요청입니다."),
    INVALID_DATA("400", "유효하지 않은 데이터입니다."),
    INVALID_STATE("400", "유효하지 않은 상태입니다."),
    INVALID_OPERATION("400", "유효하지 않은 작업입니다."),
    INVALID_FILE("400", "유효하지 않은 파일입니다."),
    INVALID_FORMAT("400", "유효하지 않은 형식입니다."),
    INVALID_EMAIL("400", "유효하지 않은 이메일입니다."),
    INVALID_CREDENTIAL("400", "유효하지 않은 자격 증명입니다."),
    INVALID_HEADER("400", "유효하지 않은 헤더입니다."),
    INVALID_COOKIE("400", "유효하지 않은 쿠키입니다."),
    INVALID_TOKEN_TYPE("400", "유효하지 않은 토큰 타입입니다."),
    INVALID_GRANT("400", "유효하지 않은 권한 입니다."),

    UNAUTHORIZED("401", "인증되지 않았습니다."),
    INVALID_TOKEN("401", "유효하지 않은 토큰입니다."),
    INVALID_AUTHORIZATION("401", "유효하지 않은 인증입니다."),
    INVALID_CREDENTIALS("401", "유효하지 않은 자격 증명입니다."),

    FORBIDDEN("403", "접근이 금지되었습니다."),
    INVALID_ACCESS("403", "유효하지 않은 접근입니다."),
    METHOD_NOT_ALLOW("405", "허용되지 않는 메소드입니다."),
    METHOD_NOT_SUPPORT("405", "지원하지 않는 메소드입니다."),

    NOT_FOUND("404", "찾을 수 없습니다."),

    CONFLICT("409", "충돌이 발생했습니다."),

    INTERNAL_SERVER_ERROR("500", "서버 내부 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE("503", "서비스를 사용할 수 없습니다."),

    FAIL("500", "실패")
    ;

    private final String code;
    private final String message;

}
