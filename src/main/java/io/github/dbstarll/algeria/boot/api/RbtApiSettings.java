package io.github.dbstarll.algeria.boot.api;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;

import static org.apache.commons.lang3.Validate.notBlank;

@Getter
@Setter
public final class RbtApiSettings extends BaseModel implements InitializingBean {
    private static final long serialVersionUID = -7282181531126188909L;

    private String uri;
    private String context = RelativeUriResolver.DEFAULT_CONTEXT;
    private String portalAccount;
    private String portalPwd;
    private String portalType;
    private String role;
    private String roleCode;

    private final ToneSettings tone = new ToneSettings();
    private final SystemSettings system = new SystemSettings();
    private final UserSettings user = new UserSettings();

    @Override
    public void afterPropertiesSet() {
        notBlank(uri, "uri not set");
        notBlank(context, "context not set");
        notBlank(portalAccount, "portalAccount not set");
        notBlank(portalPwd, "portalPwd not set");
        notBlank(portalType, "tone.portalType not set");
        notBlank(role, "role not set");
        notBlank(roleCode, "roleCode not set");

        tone.afterPropertiesSet();
        system.afterPropertiesSet();
        user.afterPropertiesSet();
    }

    @Getter
    @Setter
    public static final class ToneSettings extends BaseModel implements InitializingBean {
        private static final long serialVersionUID = 5492040638431511670L;

        private String catalogId;

        @Override
        public void afterPropertiesSet() {
            notBlank(catalogId, "tone.catalogId not set");
        }
    }

    @Getter
    @Setter
    public static final class SystemSettings extends BaseModel implements InitializingBean {
        private static final long serialVersionUID = -1319165674474641536L;

        private String smLabel;

        @Override
        public void afterPropertiesSet() {
            notBlank(smLabel, "system.smLabel not set");
        }
    }

    @Getter
    @Setter
    public static final class UserSettings extends BaseModel implements InitializingBean {
        private static final long serialVersionUID = 8186591634904115152L;

        private String productId;

        @Override
        public void afterPropertiesSet() {
            notBlank(productId, "user.productId not set");
        }
    }
}
