package seg.work.carog.server.accident.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccidentCostInfoResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String company;
    private BigDecimal additionalPrice;
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private Instant time;

    public AccidentCostInfoResponse(AccidentCostInfoEntity accidentCostInfoEntity) {
        this.id = accidentCostInfoEntity.getId();
        this.carInfoId = accidentCostInfoEntity.getCarInfoId();
        this.type = accidentCostInfoEntity.getType();
        this.price = accidentCostInfoEntity.getPrice();
        this.company = accidentCostInfoEntity.getCompany();
        this.additionalPrice = accidentCostInfoEntity.getAdditionalPrice();
        this.memo = accidentCostInfoEntity.getMemo();
        this.date = accidentCostInfoEntity.getDate();
        this.time = accidentCostInfoEntity.getTime();
    }
}
