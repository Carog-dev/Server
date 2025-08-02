package seg.work.carog.server.accident.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccidentCostInfoSaveRequest {

    private Long carInfoId;

    @NotBlank
    private String type;

    @Min(0)
    @NotBlank
    private BigDecimal price;

    @NotBlank
    private String company;

    @Min(0)
    @NotBlank
    private BigDecimal additionalPrice;

    private String memo;

    @NotBlank
    private LocalDate date;

    private Instant time;

    public AccidentCostInfoEntity toEntity(Long carInfoId) {
        return AccidentCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .type(this.type)
                .price(this.price)
                .company(this.company)
                .additionalPrice(this.additionalPrice)
                .memo(this.memo)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
