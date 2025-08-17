package seg.work.carog.server.common.config.async;

import java.util.Map;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class LoggingTaskDecorator implements TaskDecorator {

    @Override
    @NonNull
    public Runnable decorate(@NonNull Runnable runnable) {
        final Map<String, String> callerThreadContext = MDC.getCopyOfContextMap();
        return () -> {
            if (callerThreadContext != null) {
                MDC.setContextMap(callerThreadContext);
            }
            runnable.run();
        };
    }
}
