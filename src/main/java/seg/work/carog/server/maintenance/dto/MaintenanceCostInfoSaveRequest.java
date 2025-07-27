package seg.work.carog.server.maintenance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCostInfoSaveRequest {

    private Long carInfoId;

    @NotBlank
    private String item;

    @Min(0)
    @NotBlank
    private BigDecimal price;

    @Min(0)
    @NotBlank
    private BigDecimal mileage;

    @NotBlank
    private String company;

    private String memo;

    @NotBlank
    private LocalDate date;

    private Instant time;

    public MaintenanceCostInfoEntity toEntity(Long carInfoId) {
        return MaintenanceCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .item(this.item)
                .price(this.price)
                .mileage(this.mileage)
                .company(this.company)
                .memo(this.memo)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
