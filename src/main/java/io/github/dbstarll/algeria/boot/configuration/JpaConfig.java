package io.github.dbstarll.algeria.boot.configuration;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JpaConfig {
    @Bean
    @ConditionalOnMissingBean(PhysicalNamingStrategy.class)
    PhysicalNamingStrategy prefixTbNamingStrategy() {
        return new PrefixTbNamingStrategy("ag_");
    }

    static final class PrefixTbNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
        private final String prefix;

        PrefixTbNamingStrategy(final String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment jdbcEnvironment) {
            final Identifier identifier = super.toPhysicalTableName(name, jdbcEnvironment);
            final String tableName = prefix + identifier;
            return getIdentifier(tableName, identifier.isQuoted(), jdbcEnvironment);
        }
    }
}
