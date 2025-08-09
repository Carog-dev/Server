package seg.work.carog.server.oil.entity;

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
import seg.work.carog.server.oil.dto.OilCostInfoUpdateRequest;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oil_cost_info")
public class OilCostInfoEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4706060992426469817L;

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
    @Column(name = "litre", nullable = false, length = 20)
    private String litre;

    @NotNull
    @Column(name = "unit", nullable = false)
    private BigDecimal unit;

    @NotNull
    @Column(name = "company", nullable = false, length = 50)
    private String company;

    @NotNull
    @Column(name = "range", nullable = false)
    private BigDecimal range;

    @NotNull
    @Column(name = "memo", nullable = false, length = 500)
    private String memo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"")
    private LocalTime time;

    public void updateOilCostInfo(OilCostInfoUpdateRequest oilCostInfoUpdateRequest) {
        this.type = oilCostInfoUpdateRequest.getType();
        this.price = oilCostInfoUpdateRequest.getPrice();
        this.litre = oilCostInfoUpdateRequest.getLitre();
        this.unit = oilCostInfoUpdateRequest.getUnit();
        this.company = oilCostInfoUpdateRequest.getCompany();
        this.range = oilCostInfoUpdateRequest.getRange();
        this.memo = oilCostInfoUpdateRequest.getMemo();
        this.date = oilCostInfoUpdateRequest.getDate();
        this.time = oilCostInfoUpdateRequest.getTime();
    }

}