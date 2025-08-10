package seg.work.carog.server.maintenance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseUpdateRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MaintenanceCostInfoUpdateRequest extends BaseUpdateRequest {

    @NotBlank
    private Long id;

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
}
