package seg.work.carog.server.installment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentCostInfoResponse {

    private Long id;
    private Long carInfoId;
    private BigDecimal price;
    private Short monthly;
    private Short round;
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private Instant time;

    public InstallmentCostInfoResponse(InstallmentCostInfoEntity installmentCostInfoEntity) {
        this.id = installmentCostInfoEntity.getId();
        this.carInfoId = installmentCostInfoEntity.getCarInfoId();
        this.price = installmentCostInfoEntity.getPrice();
        this.monthly = installmentCostInfoEntity.getMonthly();
        this.round = installmentCostInfoEntity.getRound();
        this.memo = installmentCostInfoEntity.getMemo();
        this.date = installmentCostInfoEntity.getDate();
        this.time = installmentCostInfoEntity.getTime();
    }
}
