package seg.work.carog.server.parking.entity;

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
import seg.work.carog.server.parking.dto.ParkingCostInfoUpdateRequest;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_cost_info")
public class ParkingCostInfoEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 7024599725766000018L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_info_id")
    private Long carInfoId;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "memo", length = 500)
    private String memo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"")
    private LocalTime time;

    public void updateParkingCostInfo(ParkingCostInfoUpdateRequest parkingCostInfoUpdateRequest) {
        this.price = parkingCostInfoUpdateRequest.getPrice();
        this.memo = parkingCostInfoUpdateRequest.getMemo();
        this.date = parkingCostInfoUpdateRequest.getDate();
        this.time = parkingCostInfoUpdateRequest.getTime();
    }

}