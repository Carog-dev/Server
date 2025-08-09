package seg.work.carog.server.maintenance.dto;

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
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "정비내역 저장 요청")
public class MaintenanceCostInfoSaveRequest extends BaseRequest {

    @Schema(description = "대표챠랑 또는 선택한 차량 아이디", example = "1")
    private Long carInfoId;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "항목", example = "엔진 오일 교체")
    private String item;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "금액", example = "50000")
    private BigDecimal price;

    @Min(0)
    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "키로수", example = "50000")
    private BigDecimal mileage;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "업체명", example = "공임나라")
    private String company;

    public MaintenanceCostInfoEntity toEntity(Long carInfoId) {
        return MaintenanceCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .item(this.item)
                .price(this.price)
                .mileage(this.mileage)
                .company(this.company)
                .memo(getMemo())
                .date(getDate())
                .time(getTime())
                .build();
    }
}
