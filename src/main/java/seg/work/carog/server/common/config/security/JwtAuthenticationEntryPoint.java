package seg.work.carog.server.common.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.dto.BaseApiResponse;
import seg.work.carog.server.common.util.ObjectUtil;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        try {
            Message message = Message.UNAUTHORIZED;

            response.setStatus(message.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(ObjectUtil.convertObjectToString(BaseApiResponse.error(message, null)));
        } catch (IOException ie) {
            log.error(ie.getMessage(), ie);
        }
    }
}
