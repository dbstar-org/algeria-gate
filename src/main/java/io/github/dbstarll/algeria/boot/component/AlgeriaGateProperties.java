package io.github.dbstarll.algeria.boot.component;

import io.github.dbstarll.algeria.boot.api.RbtApiSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

import static org.apache.commons.lang3.Validate.notBlank;

@Component
@ConfigurationProperties(prefix = "algeria-gate")
@Getter
public final class AlgeriaGateProperties implements InitializingBean {
    /**
     * 外部API相关配置项.
     */
    private final ApiSettings api = new ApiSettings();

    /**
     * Web配置项.
     */
    private final WebSettings web = new WebSettings();

    @Setter
    private String verifyCode;

    @Setter
    private String gameRoot;

    @Override
    public void afterPropertiesSet() {
        api.afterPropertiesSet();
        notBlank(gameRoot, "game-root not set");
    }

    @Getter
    public static final class ApiSettings implements InitializingBean {
        private final RbtApiSettings rbt = new RbtApiSettings();

        @Override
        public void afterPropertiesSet() {
            rbt.afterPropertiesSet();
        }
    }

    @Getter
    public static final class WebSettings {
        /**
         * CORS配置项.
         */
        private final CorsSettings cors = new CorsSettings();

        @Setter
        public static final class CorsSettings {
            /**
             * the origins for which cross-origin requests are allowed from a browser.
             */
            private String[] allowedOrigins;

            /**
             * 定制CorsRegistration.
             *
             * @param cors CorsRegistration
             */
            public void customize(final CorsRegistration cors) {
                if (allowedOrigins != null) {
                    cors.allowedOrigins(allowedOrigins);
                }
            }
        }
    }
}
