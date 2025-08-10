package seg.work.carog.server.installment.entity;

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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import seg.work.carog.server.common.entity.BaseEntity;
import seg.work.carog.server.installment.dto.InstallmentCostInfoUpdateRequest;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "installment_cost_info")
public class InstallmentCostInfoEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 9051752320905284179L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_info_id")
    private Long carInfoId;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "monthly", nullable = false)
    private Short monthly;

    @NotNull
    @Column(name = "round", nullable = false)
    private Short round;

    @Column(name = "memo", length = 500)
    private String memo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"")
    private LocalTime time;

    public void updateInstallmentCostInfo(InstallmentCostInfoUpdateRequest installmentCostInfoUpdateRequest) {
        this.price = installmentCostInfoUpdateRequest.getPrice();
        this.monthly = installmentCostInfoUpdateRequest.getMonthly();
        this.round = installmentCostInfoUpdateRequest.getRound();
        this.memo = installmentCostInfoUpdateRequest.getMemo();
        this.date = installmentCostInfoUpdateRequest.getDate();
        this.time = installmentCostInfoUpdateRequest.getTime();
    }
}