package seg.work.carog.server.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "차량 정보 수정 요청")
public class CarInfoUpdateRequest extends BaseRequest {

    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "차량 아이디", example = "1")
    private Long id;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "차량명", example = "LF소나타")
    private String name;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "차량 번호", example = "65보 6191")
    private String number;
}
