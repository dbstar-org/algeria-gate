package io.github.dbstarll.algeria.boot.uuid;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * @author dbstar
 */
public final class StringToUuidConverter implements Converter<String, UUID> {
    @Override
    public UUID convert(@NonNull final String source) {
        return Uuid.fromString(source);
    }
}
