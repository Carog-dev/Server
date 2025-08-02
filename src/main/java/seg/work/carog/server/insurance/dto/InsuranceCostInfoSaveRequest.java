package seg.work.carog.server.insurance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCostInfoSaveRequest {

    private Long carInfoId;

    @NotBlank
    private String type;

    @Min(0)
    @NotBlank
    private BigDecimal price;

    private String memo;

    @NotBlank
    private LocalDate date;

    private Instant time;

    public InsuranceCostInfoEntity toEntity(Long carInfoId) {
        return InsuranceCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .type(this.type)
                .price(this.price)
                .memo(this.memo)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
