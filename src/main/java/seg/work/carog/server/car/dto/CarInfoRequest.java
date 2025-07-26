package seg.work.carog.server.car.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seg.work.carog.server.car.entity.CarInfoEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoRequest {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    @NotBlank
    private Boolean represent;

    public CarInfoEntity toEntity(Long userId) {
        return CarInfoEntity.builder()
                .userId(userId)
                .name(this.name)
                .number(this.number)
                .represent(this.represent)
                .build();
    }
}
