package seg.work.carog.server.installment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseUpdateRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "할부 수정 요청")
public class InstallmentCostInfoUpdateRequest extends BaseUpdateRequest {

    @NotBlank
    @Schema(description = "유류비 아이디", example = "1")
    private Long id;

    @Schema(description = "대표챠랑 또는 선택한 차량 아이디", example = "1")
    private Long carInfoId;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "금액", example = "1200000")
    private BigDecimal price;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "전체 할부 개월", example = "60")
    private Short monthly;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "납부 회차", example = "12")
    private Short round;
}
