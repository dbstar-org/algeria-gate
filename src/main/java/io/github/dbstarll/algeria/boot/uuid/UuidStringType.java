package io.github.dbstarll.algeria.boot.uuid;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import java.util.UUID;

/**
 * A type mapping {@link java.sql.Types#VARCHAR} and {@link UUID}.
 *
 * @author dbstar
 */
public final class UuidStringType extends AbstractSingleColumnStandardBasicType<UUID> {
    private static final long serialVersionUID = 8261820543552739066L;

    static final UuidStringType INSTANCE = new UuidStringType();

    static final String NAME = "uuid-string-22";

    private UuidStringType() {
        super(VarcharTypeDescriptor.INSTANCE, UuidTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
