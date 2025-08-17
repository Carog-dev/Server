package seg.work.carog.server.common.config.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setQueueCapacity(10);
        threadPoolTaskExecutor.setTaskDecorator(new LoggingTaskDecorator());
        threadPoolTaskExecutor.setThreadNamePrefix("Async-threadPool-");
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}