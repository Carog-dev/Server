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
@Schema(description = "대표 차량 수정 요청")
public class CarInfoChangeRepresentRequest extends BaseRequest {

    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "차량 아이디", example = "1")
    private Long id;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "대표차량 지정 여부", example = "Y")
    private Boolean represent;
}
