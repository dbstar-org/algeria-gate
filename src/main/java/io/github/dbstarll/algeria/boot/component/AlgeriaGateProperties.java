package io.github.dbstarll.algeria.boot.component;

import io.github.dbstarll.algeria.boot.api.RbtApiSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "algeria-gate")
@Getter
public final class AlgeriaGateProperties implements InitializingBean {
    /**
     * 外部API相关配置项.
     */
    private final ApiSettings api = new ApiSettings();

    @Setter
    private String verifyCode;

    @Override
    public void afterPropertiesSet() {
        api.afterPropertiesSet();
    }

    @Getter
    public static final class ApiSettings implements InitializingBean {
        private final RbtApiSettings rbt = new RbtApiSettings();

        @Override
        public void afterPropertiesSet() {
            rbt.afterPropertiesSet();
        }
    }
}
