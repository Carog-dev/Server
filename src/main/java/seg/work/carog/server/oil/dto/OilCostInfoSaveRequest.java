package seg.work.carog.server.oil.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseSaveRequest;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OilCostInfoSaveRequest extends BaseSaveRequest {

    private Long carInfoId;

    @NotBlank
    private String type;

    @Min(0)
    @NotBlank
    private BigDecimal price;

    @NotBlank
    private String litre;

    @Min(0)
    @NotBlank
    private BigDecimal unit;

    @NotBlank
    private String company;

    @Min(0)
    @NotBlank
    private BigDecimal range;

    public OilCostInfoEntity toEntity(Long carInfoId) {
        return OilCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .type(this.type)
                .price(this.price)
                .litre(this.litre)
                .unit(this.unit)
                .company(this.company)
                .range(this.range)
                .memo(getMemo())
                .date(getDate())
                .time(getTime())
                .build();
    }
}
