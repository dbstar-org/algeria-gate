package io.github.dbstarll.algeria.boot.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author dbstar
 */
@Configuration
class RedisConfig {
    @Bean
    @ConditionalOnMissingBean
    ValueOperations<Object, Object> valueOperations(final RedisTemplate<Object, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    @ConditionalOnMissingBean
    ValueOperations<String, String> stringValueOperations(final RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }
}
