package io.github.dbstarll.algeria.boot.autoconfigure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.dbstarll.algeria.boot.uuid.StringToUuidConverter;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.util.UUID;

/**
 * @author dbstar
 */
@AutoConfiguration
class UuidAutoConfiguration {
    @Bean
    @ConditionalOnClass(Converter.class)
    @ConditionalOnMissingBean(StringToUuidConverter.class)
    StringToUuidConverter stringToUuidConverter() {
        return new StringToUuidConverter();
    }

    @JsonComponent
    static class UuidJsonSerializer extends JsonSerializer<UUID> {
        @Override
        public void serialize(final UUID value, final JsonGenerator gen, final SerializerProvider serializers)
                throws IOException {
            gen.writeString(Uuid.toString(value));
        }
    }

    @JsonComponent
    static class UuidJsonDeserializer extends JsonDeserializer<UUID> {
        @Override
        public UUID deserialize(final JsonParser p, final DeserializationContext context) throws IOException {
            return Uuid.fromString(p.getText());
        }
    }
}
