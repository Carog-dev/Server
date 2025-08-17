package seg.work.carog.server.oil.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OilCostInfoResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String liter;
    private BigDecimal unit;
    private String company;
    private BigDecimal range;
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    public OilCostInfoResponse(OilCostInfoEntity oilCostInfoEntity) {
        this.id = oilCostInfoEntity.getId();
        this.carInfoId = oilCostInfoEntity.getCarInfoId();
        this.type = oilCostInfoEntity.getType();
        this.price = oilCostInfoEntity.getPrice();
        this.liter = oilCostInfoEntity.getLiter();
        this.unit = oilCostInfoEntity.getUnit();
        this.company = oilCostInfoEntity.getCompany();
        this.range = oilCostInfoEntity.getRange();
        this.memo = oilCostInfoEntity.getMemo();
        this.date = oilCostInfoEntity.getDate();
        this.time = oilCostInfoEntity.getTime();
    }
}
