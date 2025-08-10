package seg.work.carog.server.oil.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class OilCostInfoUpdateRequest extends BaseUpdateRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private String type;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @NotBlank
    private String litre;

    @Min(0)
    @NotNull
    private BigDecimal unit;

    @NotBlank
    private String company;

    @Min(0)
    @NotNull
    private BigDecimal range;
}
