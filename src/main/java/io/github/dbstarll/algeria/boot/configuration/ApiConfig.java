package io.github.dbstarll.algeria.boot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import io.github.dbstarll.utils.lang.security.InstanceException;
import io.github.dbstarll.utils.lang.security.SecureRandomAlgorithm;
import io.github.dbstarll.utils.lang.security.SecurityFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class ApiConfig {
    @Bean
    @ConditionalOnMissingBean
    SecureRandom secureRandom() throws InstanceException, NoSuchAlgorithmException {
        return SecurityFactory.builder(SecureRandomAlgorithm.SHA1_PRNG).build();
    }

    @Configuration
    @ConditionalOnClass(RbtApi.class)
    static class RbtApiConfiguration {
        @Bean
        @ConditionalOnMissingBean(RbtApi.class)
        RbtApi rbtApi(final HttpClient httpClient, final ObjectMapper mapper, final AlgeriaGateProperties settings) {
            return new RbtApi(httpClient, mapper, settings.getApi().getRbt());
        }
    }
}
