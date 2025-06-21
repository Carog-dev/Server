package seg.work.carog.server.common.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
