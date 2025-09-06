package seg.work.carog.server.insurance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InsuranceCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String company;

    public InsuranceCostInfoResponse(InsuranceCostInfoEntity insuranceCostInfoEntity) {
        this.id = insuranceCostInfoEntity.getId();
        this.carInfoId = insuranceCostInfoEntity.getCarInfoId();
        this.type = insuranceCostInfoEntity.getType();
        this.price = insuranceCostInfoEntity.getPrice();
        this.company = insuranceCostInfoEntity.getCompany();
        setMemo(insuranceCostInfoEntity.getMemo());
        setDate(insuranceCostInfoEntity.getDate());
        setTime(insuranceCostInfoEntity.getTime());
        setCreatedAt(insuranceCostInfoEntity.getCreatedAt());
        setUpdatedAt(insuranceCostInfoEntity.getUpdatedAt());
    }
}
