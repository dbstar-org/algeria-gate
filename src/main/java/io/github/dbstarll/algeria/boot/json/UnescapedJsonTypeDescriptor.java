package io.github.dbstarll.algeria.boot.json;

import com.vladmihalcea.hibernate.type.json.internal.JsonTypeDescriptor;
import com.vladmihalcea.hibernate.type.util.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Optional;

/**
 * @author dbstar
 */
public final class UnescapedJsonTypeDescriptor extends JsonTypeDescriptor {
    private static final long serialVersionUID = -2437252778218560456L;

    UnescapedJsonTypeDescriptor() {
        super(Configuration.INSTANCE.getObjectMapperWrapper());
    }

    @Override
    public Object fromString(final String string) {
        return super.fromString(unescapeJson(string));
    }

    private String unescapeJson(final String string) {
        return Optional.of(string)
                .filter(s -> StringUtils.startsWith(s, "\""))
                .filter(s -> StringUtils.endsWith(s, "\""))
                .map(s -> s.substring(1, s.length() - 1))
                .map(StringEscapeUtils::unescapeJson)
                .orElse(string);
    }
}
