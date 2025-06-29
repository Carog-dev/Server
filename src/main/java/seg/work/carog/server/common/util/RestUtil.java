package seg.work.carog.server.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestUtil {

    private static ObjectMapper objectMapper;

    public static String convertObjectToString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (objectMapper == null) {
                ObjectMapper om = new ObjectMapper();
                om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                om.registerModules(new Jdk8Module(), new JavaTimeModule());
                return om.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } else {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
        } catch (Exception e) {
            log.error("RestUtil : convertObjectToString method error", e);
            return "error";
        }
    }
}
