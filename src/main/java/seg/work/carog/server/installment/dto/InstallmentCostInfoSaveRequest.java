package seg.work.carog.server.installment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentCostInfoSaveRequest {

    private Long carInfoId;

    @Min(0)
    @NotBlank
    private BigDecimal price;

    @Min(0)
    @NotBlank
    private Short monthly;

    @Min(0)
    @NotBlank
    private Short round;

    private String memo;

    @NotBlank
    private LocalDate date;

    private Instant time;

    public InstallmentCostInfoEntity toEntity(Long carInfoId) {
        return InstallmentCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .price(this.price)
                .monthly(this.monthly)
                .round(this.round)
                .memo(this.memo)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
