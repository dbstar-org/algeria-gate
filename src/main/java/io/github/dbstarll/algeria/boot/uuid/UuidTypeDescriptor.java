package io.github.dbstarll.algeria.boot.uuid;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.util.UUID;

/**
 * @author dbstar
 */
public final class UuidTypeDescriptor extends AbstractTypeDescriptor<UUID> {
    private static final long serialVersionUID = -4196641070928817623L;

    static final UuidTypeDescriptor INSTANCE = new UuidTypeDescriptor();

    private UuidTypeDescriptor() {
        super(UUID.class);
    }

    @Override
    public UUID fromString(final String string) {
        return Uuid.fromString(string);
    }

    @Override
    public String toString(final UUID value) {
        return Uuid.toString(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> X unwrap(final UUID value, final Class<X> type, final WrapperOptions options) {
        if (value == null) {
            return null;
        } else if (UUID.class == type) {
            return (X) value;
        } else if (String.class == type) {
            return (X) toString(value);
        } else {
            throw unknownUnwrap(type);
        }
    }

    @Override
    public <X> UUID wrap(final X value, final WrapperOptions options) {
        if (value == null) {
            return null;
        } else if (value instanceof UUID) {
            return (UUID) value;
        } else if (value instanceof String) {
            return fromString((String) value);
        } else {
            throw unknownWrap(value.getClass());
        }
    }

    @Override
    protected HibernateException unknownUnwrap(final Class conversionType) {
        return new HibernateException(
                "Unknown unwrap conversion requested: " + getJavaType().getName() + " to " + conversionType.getName()
        );
    }

    @Override
    protected HibernateException unknownWrap(final Class conversionType) {
        return new HibernateException(
                "Unknown wrap conversion requested: " + conversionType.getName() + " to " + getJavaType().getName()
        );
    }
}
