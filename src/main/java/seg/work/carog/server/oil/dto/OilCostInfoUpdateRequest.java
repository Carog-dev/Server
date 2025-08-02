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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OilCostInfoUpdateRequest {

    @NotBlank
    private Long id;

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

    private LocalDate date;
    private Instant time;
}
