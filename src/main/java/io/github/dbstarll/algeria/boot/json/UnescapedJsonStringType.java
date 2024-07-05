package io.github.dbstarll.algeria.boot.json;

import com.vladmihalcea.hibernate.type.AbstractHibernateType;
import com.vladmihalcea.hibernate.type.json.internal.JsonStringSqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.json.internal.JsonTypeDescriptor;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * @author dbstar
 */
public final class UnescapedJsonStringType extends AbstractHibernateType<Object> implements DynamicParameterizedType {
    private static final long serialVersionUID = -1933572074524757803L;

    static final String NAME = "json-unescaped";

    /**
     * 去掉转义符的JsonStringType.
     */
    public UnescapedJsonStringType() {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new UnescapedJsonTypeDescriptor());
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setParameterValues(final Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
