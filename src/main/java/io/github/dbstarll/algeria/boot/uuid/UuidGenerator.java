package io.github.dbstarll.algeria.boot.uuid;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

/**
 * @author dbstar
 */
public final class UuidGenerator implements IdentifierGenerator {
    @Override
    public UUID generate(final SharedSessionContractImplementor session, final Object object) {
        return Uuid.generate();
    }
}
