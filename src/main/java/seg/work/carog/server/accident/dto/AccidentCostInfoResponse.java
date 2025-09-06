package seg.work.carog.server.accident.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;
import seg.work.carog.server.common.dto.BaseResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccidentCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String company;
    private BigDecimal additionalPrice;

    public AccidentCostInfoResponse(AccidentCostInfoEntity accidentCostInfoEntity) {
        this.id = accidentCostInfoEntity.getId();
        this.carInfoId = accidentCostInfoEntity.getCarInfoId();
        this.type = accidentCostInfoEntity.getType();
        this.price = accidentCostInfoEntity.getPrice();
        this.company = accidentCostInfoEntity.getCompany();
        this.additionalPrice = accidentCostInfoEntity.getAdditionalPrice();
        setMemo(accidentCostInfoEntity.getMemo());
        setDate(accidentCostInfoEntity.getDate());
        setTime(accidentCostInfoEntity.getTime());
        setCreatedAt(accidentCostInfoEntity.getCreatedAt());
        setUpdatedAt(accidentCostInfoEntity.getUpdatedAt());
    }
}
