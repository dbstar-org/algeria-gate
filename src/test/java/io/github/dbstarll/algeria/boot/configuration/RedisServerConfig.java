package io.github.dbstarll.algeria.boot.configuration;

import com.github.fppt.jedismock.RedisServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author dbstar
 */
@Configuration
class RedisServerConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    RedisServer redisServer() {
        return RedisServer.newRedisServer(26379);
    }

    @Bean
    @Primary
    @DependsOn("redisServer")
    ValueOperations<Object, Object> valueOperations(final RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }
}
