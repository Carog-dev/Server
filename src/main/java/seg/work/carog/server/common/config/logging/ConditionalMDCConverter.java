package seg.work.carog.server.common.config.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ConditionalMDCConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String traceId = event.getMDCPropertyMap().get("traceId");
        return (traceId != null && !traceId.isEmpty()) ? "[traceId=" + traceId + "]" : "";
    }
}