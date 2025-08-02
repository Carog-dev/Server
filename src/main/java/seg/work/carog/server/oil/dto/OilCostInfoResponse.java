package seg.work.carog.server.oil.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OilCostInfoResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String litre;
    private BigDecimal unit;
    private String company;
    private BigDecimal range;
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private Instant time;

    public OilCostInfoResponse(OilCostInfoEntity oilCostInfoEntity) {
        this.id = oilCostInfoEntity.getId();
        this.carInfoId = oilCostInfoEntity.getCarInfoId();
        this.type = oilCostInfoEntity.getType();
        this.price = oilCostInfoEntity.getPrice();
        this.litre = oilCostInfoEntity.getLitre();
        this.unit = oilCostInfoEntity.getUnit();
        this.company = oilCostInfoEntity.getCompany();
        this.range = oilCostInfoEntity.getRange();
        this.memo = oilCostInfoEntity.getMemo();
        this.date = oilCostInfoEntity.getDate();
        this.time = oilCostInfoEntity.getTime();
    }
}
