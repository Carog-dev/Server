package seg.work.carog.server.car.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.common.constant.Constant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoSaveRequest {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    public CarInfoEntity toEntity(Long userId) {
        return CarInfoEntity.builder()
                .userId(userId)
                .name(this.name)
                .number(this.number)
                .represent(Constant.FLAG_FALSE)
                .build();
    }
}
