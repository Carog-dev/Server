package seg.work.carog.server.common.config.logging;

import ch.qos.logback.core.pattern.DynamicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ConditionalMDCParamConverter extends DynamicConverter<ILoggingEvent> {

    @Override
    public String convert(ILoggingEvent event) {
        String key = getFirstOption();
        if (key == null) return "";

        String value = event.getMDCPropertyMap().get(key);
        if (value != null && !value.isEmpty()) {
            return "[" + key + "=" + value + "]";
        } else {
            return "";
        }
    }
}