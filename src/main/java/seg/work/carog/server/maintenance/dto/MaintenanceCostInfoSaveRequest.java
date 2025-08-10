package seg.work.carog.server.maintenance.dto;

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
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MaintenanceCostInfoSaveRequest extends BaseSaveRequest {

    private Long carInfoId;

    @NotBlank
    private String item;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotNull
    private BigDecimal mileage;

    @NotBlank
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
