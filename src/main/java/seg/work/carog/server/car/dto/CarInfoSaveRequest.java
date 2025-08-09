package seg.work.carog.server.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.dto.BaseRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "차량 정보 저장 요청")
public class CarInfoSaveRequest extends BaseRequest {

    private Long id;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "차량명", example = "LF소나타")
    private String name;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "차량 번호", example = "65보 6191")
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
