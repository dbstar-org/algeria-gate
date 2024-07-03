package io.github.dbstarll.algeria.boot.configuration;

import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dbstar
 */
@Configuration
class WebConfig {
    @Bean
    WebMvcConfigurer corsConfigurer(final AlgeriaGateProperties algeriaGateProperties) {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(@NonNull final CorsRegistry registry) {
                customize(registry.addMapping("/api/**"));
            }

            private void customize(final CorsRegistration cors) {
                algeriaGateProperties.getWeb().getCors().customize(
                        cors.allowCredentials(false)
                                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name()));
            }
        };
    }
}
