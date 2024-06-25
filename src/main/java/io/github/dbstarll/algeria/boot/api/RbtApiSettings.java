package io.github.dbstarll.algeria.boot.api;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;

import javax.validation.constraints.NotBlank;

import static org.apache.commons.lang3.Validate.notBlank;

@Getter
@Setter
public final class RbtApiSettings extends BaseModel implements InitializingBean {
    private static final long serialVersionUID = -7282181531126188909L;

    private String uri;
    private String context = RelativeUriResolver.DEFAULT_CONTEXT;
    private String portalAccount;
    private String portalPwd;
    private String role;
    private String roleCode;

    private final ToneSettings tone = new ToneSettings();

    @Override
    public void afterPropertiesSet() {
        notBlank(uri, "uri not set");
        notBlank(context, "context not set");
        notBlank(portalAccount, "portalAccount not set");
        notBlank(portalPwd, "portalPwd not set");
        notBlank(role, "role not set");
        notBlank(roleCode, "roleCode not set");

        tone.afterPropertiesSet();
    }

    @Getter
    @Setter
    public static final class ToneSettings extends BaseModel implements InitializingBean {
        private static final long serialVersionUID = 5492040638431511670L;

        private String portalType;
        private String catalogId;

        @Override
        public void afterPropertiesSet() {
            notBlank(portalType, "tone.portalType not set");
            notBlank(catalogId, "tone.catalogId not set");
        }
    }
}
