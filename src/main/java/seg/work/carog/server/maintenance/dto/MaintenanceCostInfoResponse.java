package seg.work.carog.server.maintenance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MaintenanceCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private String item;
    private BigDecimal price;
    private BigDecimal mileage;
    private String company;

    public MaintenanceCostInfoResponse(MaintenanceCostInfoEntity maintenanceCostInfoEntity) {
        this.id = maintenanceCostInfoEntity.getId();
        this.carInfoId = maintenanceCostInfoEntity.getCarInfoId();
        this.item = maintenanceCostInfoEntity.getItem();
        this.price = maintenanceCostInfoEntity.getPrice();
        this.mileage = maintenanceCostInfoEntity.getMileage();
        this.company = maintenanceCostInfoEntity.getCompany();
        setMemo(maintenanceCostInfoEntity.getMemo());
        setDate(maintenanceCostInfoEntity.getDate());
        setTime(maintenanceCostInfoEntity.getTime());
        setCreatedAt(maintenanceCostInfoEntity.getCreatedAt());
        setUpdatedAt(maintenanceCostInfoEntity.getUpdatedAt());
    }
}
