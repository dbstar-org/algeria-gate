package io.github.dbstarll.algeria.boot.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dbstar
 */
@Configuration
class SpringDocConfig {
    @Bean
    OpenAPI apiDev(@Value("${algeria-gate.version:v1.0.0-SNAPSHOT}") final String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Algeria Gate API")
                        .description("超级门户 API 演示")
                        .version(version)
                        .contact(new Contact()
                                .name("代波")
                                .email("dbstar@y1cloud.com")));
    }
}
