package io.github.dbstarll.algeria.boot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class ApiConfig {
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
