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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentCostInfoUpdateRequest {

    @NotBlank
    private Long id;

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
}
