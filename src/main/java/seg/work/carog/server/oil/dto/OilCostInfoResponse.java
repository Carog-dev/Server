package seg.work.carog.server.oil.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OilCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String liter;
    private BigDecimal unit;
    private String company;
    private BigDecimal range;

    public OilCostInfoResponse(OilCostInfoEntity oilCostInfoEntity) {
        this.id = oilCostInfoEntity.getId();
        this.carInfoId = oilCostInfoEntity.getCarInfoId();
        this.type = oilCostInfoEntity.getType();
        this.price = oilCostInfoEntity.getPrice();
        this.liter = oilCostInfoEntity.getLiter();
        this.unit = oilCostInfoEntity.getUnit();
        this.company = oilCostInfoEntity.getCompany();
        this.range = oilCostInfoEntity.getRange();
        setMemo(oilCostInfoEntity.getMemo());
        setDate(oilCostInfoEntity.getDate());
        setTime(oilCostInfoEntity.getTime());
    }
}
