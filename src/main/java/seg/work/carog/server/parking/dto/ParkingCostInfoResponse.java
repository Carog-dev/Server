package seg.work.carog.server.parking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.parking.entity.ParkingCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ParkingCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private BigDecimal price;

    public ParkingCostInfoResponse(ParkingCostInfoEntity parkingCostInfoEntity) {
        this.id = parkingCostInfoEntity.getId();
        this.carInfoId = parkingCostInfoEntity.getCarInfoId();
        this.price = parkingCostInfoEntity.getPrice();
        setMemo(parkingCostInfoEntity.getMemo());
        setDate(parkingCostInfoEntity.getDate());
        setTime(parkingCostInfoEntity.getTime());
        setCreatedAt(parkingCostInfoEntity.getCreatedAt());
        setUpdatedAt(parkingCostInfoEntity.getUpdatedAt());
    }
}
