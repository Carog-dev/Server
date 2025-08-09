package seg.work.carog.server.insurance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
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
@Schema(description = "보험료/세금 수정 요청")
public class InsuranceCostInfoUpdateRequest extends BaseRequest {

    @NotBlank
    @Schema(description = "유류비 아이디", example = "1")
    private Long id;

    @Schema(description = "대표챠랑 또는 선택한 차량 아이디", example = "1")
    private Long carInfoId;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "보험료/세금 ( insurance | duty )", example = "insurance")
    private String type;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "금액", example = "1200000")
    private BigDecimal price;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "업체명", example = "DB손해보험")
    private String company;
}
