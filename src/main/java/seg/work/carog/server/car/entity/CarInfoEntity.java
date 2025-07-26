package seg.work.carog.server.car.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import seg.work.carog.server.car.dto.CarInfoUpdateRequest;
import seg.work.carog.server.common.entity.BaseEntity;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_info")
public class CarInfoEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1670901009691279360L;

    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @ColumnDefault("false")
    @Column(name = "represent")
    private Boolean represent;

    public void update(CarInfoUpdateRequest carInfoUpdateRequest) {
        this.name = carInfoUpdateRequest.getName();
        this.number = carInfoUpdateRequest.getNumber();
    }

    public void updateRepresent(Boolean represent) {
        this.represent = represent;
    }
}