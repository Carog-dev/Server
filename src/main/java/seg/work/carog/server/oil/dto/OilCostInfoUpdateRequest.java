package seg.work.carog.server.oil.dto;

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
@Schema(description = "유류비 수정 요청")
public class OilCostInfoUpdateRequest extends BaseRequest {

    @NotBlank
    @Schema(description = "유류비 아이디", example = "1")
    private Long id;

    @Schema(description = "대표챠랑 또는 선택한 차량 아이디", example = "1")
    private Long carInfoId;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "유종 ( gasoline | diesel | lpg | premium gasoline )", example = "gasoline")
    private String type;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "총 주유 금액", example = "50000")
    private BigDecimal price;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "주유량(L)", example = "30.55")
    private String litre;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "리터당 단가", example = "1636")
    private BigDecimal unit;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "업체명", example = "현대셀프주유소")
    private String company;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "키로수", example = "550.51")
    private BigDecimal range;
}
