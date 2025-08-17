package seg.work.carog.server.insurance.dto;

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
import seg.work.carog.server.common.dto.BaseSaveRequest;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsuranceCostInfoSaveRequest extends BaseSaveRequest {

    private Long carInfoId;

    @NotBlank
    private String type;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @NotBlank
    private String company;

    public InsuranceCostInfoEntity toEntity(Long carInfoId) {
        return InsuranceCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .type(this.type)
                .price(this.price)
                .company(this.company)
                .memo(getMemo())
                .date(getDate())
                .time(getTime())
                .build();
    }
}
