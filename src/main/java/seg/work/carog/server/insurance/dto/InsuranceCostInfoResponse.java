package seg.work.carog.server.insurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCostInfoResponse {

    private Long id;
    private Long carInfoId;
    private String type;
    private BigDecimal price;
    private String company;
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    public InsuranceCostInfoResponse(InsuranceCostInfoEntity insuranceCostInfoEntity) {
        this.id = insuranceCostInfoEntity.getId();
        this.carInfoId = insuranceCostInfoEntity.getCarInfoId();
        this.type = insuranceCostInfoEntity.getType();
        this.price = insuranceCostInfoEntity.getPrice();
        this.company = insuranceCostInfoEntity.getCompany();
        this.memo = insuranceCostInfoEntity.getMemo();
        this.date = insuranceCostInfoEntity.getDate();
        this.time = insuranceCostInfoEntity.getTime();
    }
}
