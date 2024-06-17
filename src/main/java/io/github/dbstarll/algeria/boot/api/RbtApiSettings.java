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

    @Override
    public void afterPropertiesSet() {
        notBlank(uri, "uri not set");
        notBlank(context, "context not set");
    }
}
