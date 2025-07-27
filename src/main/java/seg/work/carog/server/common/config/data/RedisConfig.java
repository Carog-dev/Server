package seg.work.carog.server.common.config.data;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import seg.work.carog.server.common.config.property.RedisConfigProperty;
import seg.work.carog.server.common.util.RedisUtil;

@Configuration
@EnableConfigurationProperties(RedisConfigProperty.class)
public class RedisConfig {

    private final RedisConfigProperty redisConfigProperty;

    public RedisConfig(RedisConfigProperty redisConfigProperty) {
        this.redisConfigProperty = redisConfigProperty;
    }

    @Bean("lettuceConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(redisConfigProperty.getSentinel().getMaster());

        List<String> nodes = redisConfigProperty.getSentinel().getNodes();
        for (String node : nodes) {
            sentinelConfig.sentinel(RedisNode.fromString(node));
        }

        sentinelConfig.setSentinelUsername(redisConfigProperty.getSentinel().getUsername());
        sentinelConfig.setSentinelPassword(RedisPassword.of(redisConfigProperty.getSentinel().getPassword()));
        sentinelConfig.setUsername(redisConfigProperty.getUsername());
        sentinelConfig.setPassword(RedisPassword.of(redisConfigProperty.getPassword()));
        sentinelConfig.setDatabase(redisConfigProperty.getDatabase());

        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        RedisUtil.setRedisTemplate(redisTemplate);

        return redisTemplate;
    }
}