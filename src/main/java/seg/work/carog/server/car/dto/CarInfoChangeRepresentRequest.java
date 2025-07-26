package seg.work.carog.server.car.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoChangeRepresentRequest {

    @Min(0)
    private Long id;

    @NotBlank
    private Boolean represent;
}
