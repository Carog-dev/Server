package seg.work.carog.server.maintenance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCostInfoResponse {

    private Long id;
    private Long carInfoId;
    private String item;
    private BigDecimal price;
    private BigDecimal mileage;
    private String company;
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    public MaintenanceCostInfoResponse(MaintenanceCostInfoEntity maintenanceCostInfoEntity) {
        this.id = maintenanceCostInfoEntity.getId();
        this.carInfoId = maintenanceCostInfoEntity.getCarInfoId();
        this.item = maintenanceCostInfoEntity.getItem();
        this.price = maintenanceCostInfoEntity.getPrice();
        this.mileage = maintenanceCostInfoEntity.getMileage();
        this.company = maintenanceCostInfoEntity.getCompany();
        this.memo = maintenanceCostInfoEntity.getMemo();
        this.date = maintenanceCostInfoEntity.getDate();
        this.time = maintenanceCostInfoEntity.getTime();
    }
}
