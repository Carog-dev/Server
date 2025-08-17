package seg.work.carog.server.common.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSaveRequest {

    private String memo;

    @NotNull
    private LocalDate date;

    private LocalTime time;
}
