package seg.work.carog.server.oil.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OilCostInfoSaveRequest {

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

    private String memo;

    @NotBlank
    private LocalDate date;

    private Instant time;

    public OilCostInfoEntity toEntity(Long carInfoId) {
        return OilCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .type(this.type)
                .price(this.price)
                .litre(this.litre)
                .unit(this.unit)
                .company(this.company)
                .range(this.range)
                .memo(this.memo)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
