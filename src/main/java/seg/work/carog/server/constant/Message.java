package seg.work.carog.server.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {

    SUCCESS_OK("200", "성공"),

    NO_CONTENT("204", "콘텐츠 없음"),

    BAD_REQUEST("400", "잘못된 요청"),
    INVALID_INPUT("400", "잘못된 입력"),
    INVALID_USER("400", "유효하지 않은 사용자"),
    INVALID_PARAMETER("400", "유효하지 않은 파라미터"),
    INVALID_REQUEST("400", "잘못된 요청"),
    INVALID_DATA("400", "유효하지 않은 데이터"),
    INVALID_STATE("400", "유효하지 않은 상태"),
    INVALID_OPERATION("400", "유효하지 않은 작업"),
    INVALID_FILE("400", "유효하지 않은 파일"),
    INVALID_FORMAT("400", "유효하지 않은 형식"),
    INVALID_EMAIL("400", "유효하지 않은 이메일"),
    INVALID_CREDENTIAL("400", "유효하지 않은 자격 증명"),
    INVALID_HEADER("400", "유효하지 않은 헤더"),
    INVALID_COOKIE("400", "유효하지 않은 쿠키"),
    INVALID_TOKEN_TYPE("400", "유효하지 않은 토큰 타입"),
    INVALID_GRANT("400", "유효하지 않은 권한 부여"),

    UNAUTHORIZED("401", "인증되지 않았습니다."),
    INVALID_TOKEN("401", "유효하지 않은 토큰"),
    INVALID_AUTHORIZATION("401", "유효하지 않은 인증"),
    INVALID_CREDENTIALS("401", "유효하지 않은 자격 증명"),

    FORBIDDEN("403", "접근이 금지되었습니다."),
    INVALID_ACCESS("403", "유효하지 않은 접근"),
    METHOD_NOT_ALLOW("405", "허용되지 않는 메소드입니다."),
    METHOD_NOT_SUPPORT("405", "지원하지 않는 메소드입니다."),

    NOT_FOUND("404", "찾을 수 없습니다."),

    CONFLICT("409", "충돌이 발생했습니다."),

    INTERNAL_SERVER_ERROR("500", "서버 내부 오류"),
    SERVICE_UNAVAILABLE("503", "서비스를 사용할 수 없습니다."),

    FAIL("500", "실패")
    ;

    private final String code;
    private final String message;

}
