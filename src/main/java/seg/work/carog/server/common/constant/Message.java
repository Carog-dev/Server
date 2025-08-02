package seg.work.carog.server.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Message {

    SUCCESS("S0000", "성공", HttpStatus.OK),

    // API
    BAD_REQUEST("EA0000", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_USE_EMAIL("EA0001", "이미 사용 중인 이메일 입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("EA0002", "찾을 수 없는 정보입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_SENT_EMAIL("EA0003", "이미 메일이 전송되었습니다. 스팸메일함 또는 메일주소를 확인하여주세요.", HttpStatus.BAD_REQUEST),
    CANNOT_SENT_EMAIL("EA0004", "메일 전송에 실패했습니다. 관리자에게 문의 바랍니다.", HttpStatus.BAD_REQUEST),
    CANNOT_FIND_EMAIL("EA0005", "인증요청되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST),
    EXPIRED_AUTH_EMAIL_REQUEST("EA0006", "인증 요청이 만료되었습니다. 재시도 부탁드립니다.", HttpStatus.BAD_REQUEST),
    ALREADY_AUTH_EMAIL("EA0007", "이미 인증된 사용자 입니다.", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOW("EA0008", "허용되지 않은 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    METHOD_NOT_SUPPORT("EA0009", "지원하지 않는 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    NOT_EXISTS_REQUEST("EA0010", "찾을 수 없는 요청 정보입니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("EA0011", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NO_DATA("EA0012", "조회된 데이터가 없습니다.", HttpStatus.NOT_FOUND),
    REQUIRED_PARAMETER_IS_NULL("EA0013", "필수 전달 정보가 누락되었습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_CAR_NUMBER("EA0014", "이미 등록된 차량번호 입니다.", HttpStatus.BAD_REQUEST),
    NO_REPRESENT_CAR_INFO("EA0015", "현재 등록된 대표 차량이 없습니다.", HttpStatus.BAD_REQUEST),
    NO_CAR_INFO("EA0016", "등록된 차량이 없습니다.", HttpStatus.BAD_REQUEST),
    NO_MAINTENANCE_COST_INFO("EA0017", "등록된 정비 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_OIL_COST_INFO("EA0018", "등록된 유류비 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_INSURANCE_COST_INFO("EA0019", "등록된 보혐료 또는 세금 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_ACCIDENT_COST_INFO("EA0020", "등록된 사고 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NO_INSTALLMENT_COST_INFO("EA0019", "등록된 할부 내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    // file
    CANNOT_GET_FILE("EF0000", "파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_PUT_FILE("EF0001", "파일을 업로드 할 수 없습니다.", HttpStatus.BAD_REQUEST),

    // AUTH
    UNAUTHORIZED("EU0000", "인증 정보가 없습니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("EU0001", "만료된 로그인 정보입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN_RE_LOGIN("EU0002", "만료된 로그인 정보입니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    LOGIN_FAIL("EU0003", "로그인에 실패하였습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("EU0004", "신뢰할 수 없는 정보입니다.", HttpStatus.FORBIDDEN),
    UNSUPPORTED_TOKEN("EU0005", "변조된 로그인 정보입니다. 다시 로그인해주세요.", HttpStatus.FORBIDDEN),

    // SNS
    KAKAO_ACCESS_TOKEN_ERROR("EK0001", "카카오 로그인에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    KAKAO_GET_USER_INFO("EK0002", "카카오 로그인에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    FAIL("EF9999", "오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public static Message fromCode(String code) {
        for (Message message : values()) {
            if (code.equals(message.code)) {
                return message;
            }
        }
        return FAIL;
    }
}
