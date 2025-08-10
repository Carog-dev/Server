package seg.work.carog.server.insurance.dto;

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
public class InsuranceCostInfoUpdateRequest extends BaseUpdateRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private String type;

    @Min(0)
    @NotBlank
    private BigDecimal price;

    @NotBlank
    private String company;
}
