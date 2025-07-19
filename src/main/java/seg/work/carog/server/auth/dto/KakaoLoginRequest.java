package seg.work.carog.server.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KakaoLoginRequest {
    @NotBlank(message = "Authorization code is required")
    private String code;
}