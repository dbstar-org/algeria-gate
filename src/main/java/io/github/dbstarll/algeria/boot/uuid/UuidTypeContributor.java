package io.github.dbstarll.algeria.boot.uuid;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;

/**
 * @author dbstar
 */
public final class UuidTypeContributor implements TypeContributor {
    @Override
    public void contribute(final TypeContributions typeContributions, final ServiceRegistry serviceRegistry) {
        typeContributions.contributeType(UuidStringType.INSTANCE);
    }
}
