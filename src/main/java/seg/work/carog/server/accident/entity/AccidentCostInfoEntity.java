package seg.work.carog.server.accident.entity;

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
import seg.work.carog.server.accident.dto.AccidentCostInfoUpdateRequest;
import seg.work.carog.server.common.entity.BaseEntity;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accident_cost_info")
public class AccidentCostInfoEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -5880583473047281482L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_info_id")
    private Long carInfoId;

    @NotNull
    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "company", nullable = false, length = 50)
    private String company;

    @NotNull
    @Column(name = "additional_price", nullable = false)
    private BigDecimal additionalPrice;

    @Column(name = "memo", length = 500)
    private String memo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"")
    private LocalTime time;

    public void updateAccidentCostInfo(AccidentCostInfoUpdateRequest accidentCostInfoUpdateRequest) {
        this.type = accidentCostInfoUpdateRequest.getType();
        this.price = accidentCostInfoUpdateRequest.getPrice();
        this.company = accidentCostInfoUpdateRequest.getCompany();
        this.additionalPrice = accidentCostInfoUpdateRequest.getAdditionalPrice();
        this.memo = accidentCostInfoUpdateRequest.getMemo();
        this.date = accidentCostInfoUpdateRequest.getDate();
        this.time = accidentCostInfoUpdateRequest.getTime();
    }
}