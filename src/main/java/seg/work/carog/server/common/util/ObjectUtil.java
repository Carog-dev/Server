package seg.work.carog.server.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObjectUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    public static String convertObjectToString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj != null ? obj : "");
        } catch (JsonProcessingException e) {
            log.error("Error converting object to string: {}", e.getMessage());
            return "{}";
        }
    }

    public static String convertObjectToStringPretty(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj != null ? obj : "");
        } catch (JsonProcessingException e) {
            log.error("Error converting object to pretty string: {}", e.getMessage());
            return "{}";
        }
    }
}
