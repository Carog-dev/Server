package seg.work.carog.server.installment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseSaveRequest;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InstallmentCostInfoSaveRequest extends BaseSaveRequest {

    private Long carInfoId;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotBlank
    private Short monthly;

    @Min(0)
    @NotBlank
    private Short round;

    public InstallmentCostInfoEntity toEntity(Long carInfoId) {
        return InstallmentCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .price(this.price)
                .monthly(this.monthly)
                .round(this.round)
                .memo(getMemo())
                .date(getDate())
                .time(getTime())
                .build();
    }
}
