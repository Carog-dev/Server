package seg.work.carog.server.installment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstallmentCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private BigDecimal price;
    private Short monthly;
    private Short round;
    private String memo;

    public InstallmentCostInfoResponse(InstallmentCostInfoEntity installmentCostInfoEntity) {
        this.id = installmentCostInfoEntity.getId();
        this.carInfoId = installmentCostInfoEntity.getCarInfoId();
        this.price = installmentCostInfoEntity.getPrice();
        this.monthly = installmentCostInfoEntity.getMonthly();
        this.round = installmentCostInfoEntity.getRound();
        setMemo(installmentCostInfoEntity.getMemo());
        setDate(installmentCostInfoEntity.getDate());
        setTime(installmentCostInfoEntity.getTime());
        setCreatedAt(installmentCostInfoEntity.getCreatedAt());
        setUpdatedAt(installmentCostInfoEntity.getUpdatedAt());
    }
}
