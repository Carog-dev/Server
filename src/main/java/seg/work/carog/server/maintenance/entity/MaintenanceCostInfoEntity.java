package seg.work.carog.server.maintenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import seg.work.carog.server.common.entity.BaseEntity;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoUpdateRequest;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maintenance_cost_info")
public class MaintenanceCostInfoEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -7368318626203322407L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_info_id")
    private Long carInfoId;

    @NotNull
    @Column(name = "item", nullable = false)
    private String item;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "mileage", nullable = false)
    private BigDecimal mileage;

    @NotNull
    @Column(name = "company", nullable = false, length = 50)
    private String company;

    @Column(name = "memo", length = 500)
    private String memo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"")
    private LocalTime time;

    public void updateMaintenanceCostInfo(MaintenanceCostInfoUpdateRequest maintenanceCostInfoUpdateRequest) {
        this.item = maintenanceCostInfoUpdateRequest.getItem();
        this.price = maintenanceCostInfoUpdateRequest.getPrice();
        this.mileage = maintenanceCostInfoUpdateRequest.getMileage();
        this.company = maintenanceCostInfoUpdateRequest.getCompany();
        this.memo = maintenanceCostInfoUpdateRequest.getMemo();
        this.date = maintenanceCostInfoUpdateRequest.getDate();
        this.time = maintenanceCostInfoUpdateRequest.getTime();
    }
}