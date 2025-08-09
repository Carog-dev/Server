package seg.work.carog.server.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {
    @Schema(description = "메모", example = "메모 내용을 입력하세요.")
    private String memo;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "날짜", example = "2023-10-27")
    private LocalDate date;

    @Schema(description = "시간 (UTC)", example = "10:15:30")
    private LocalTime time;
}
