package seg.work.carog.server.etc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.etc.entity.EtcCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EtcCostInfoResponse extends BaseResponse {

    private Long id;
    private Long carInfoId;
    private String item;
    private BigDecimal price;

    public EtcCostInfoResponse(EtcCostInfoEntity etcCostInfoEntity) {
        this.id = etcCostInfoEntity.getId();
        this.carInfoId = etcCostInfoEntity.getCarInfoId();
        this.item = etcCostInfoEntity.getItem();
        this.price = etcCostInfoEntity.getPrice();
        setMemo(etcCostInfoEntity.getMemo());
        setDate(etcCostInfoEntity.getDate());
        setTime(etcCostInfoEntity.getTime());
        setCreatedAt(etcCostInfoEntity.getCreatedAt());
        setUpdatedAt(etcCostInfoEntity.getUpdatedAt());
    }
}
