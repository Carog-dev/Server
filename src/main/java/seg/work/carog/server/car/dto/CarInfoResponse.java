package seg.work.carog.server.car.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.car.entity.CarInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CarInfoResponse {
    private Long id;
    private String name;
    private String number;
    private Boolean represent;

    public CarInfoResponse(CarInfoEntity carInfoEntity) {
        this.id = carInfoEntity.getId();
        this.name = carInfoEntity.getName();
        this.number = carInfoEntity.getNumber();
        this.represent = carInfoEntity.getRepresent();
    }
}
