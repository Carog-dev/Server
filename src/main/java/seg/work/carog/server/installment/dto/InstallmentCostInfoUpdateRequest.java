package seg.work.carog.server.installment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstallmentCostInfoUpdateRequest extends BaseUpdateRequest {

    @NotNull
    private Long id;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotNull
    private Short monthly;

    @Min(0)
    @NotNull
    private Short round;
}
