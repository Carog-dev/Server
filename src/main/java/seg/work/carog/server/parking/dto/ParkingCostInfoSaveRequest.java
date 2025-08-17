package seg.work.carog.server.parking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseSaveRequest;
import seg.work.carog.server.parking.entity.ParkingCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ParkingCostInfoSaveRequest extends BaseSaveRequest {

    private Long carInfoId;

    @Min(0)
    @NotNull
    private BigDecimal price;

    public ParkingCostInfoEntity toEntity(Long carInfoId) {
        return ParkingCostInfoEntity.builder()
                .carInfoId(carInfoId)
                .price(this.price)
                .memo(getMemo())
                .date(getDate())
                .time(getTime())
                .build();
    }
}
