package com.simitchiyski.lockregistry.config.queue;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

import static java.time.ZoneId.of;
import static java.util.Objects.isNull;

@Component
@ConfigurationPropertiesBinding
public class ZoneIdConverter implements Converter<String, ZoneId> {
    @Override
    public ZoneId convert(final String source) {
        if (isNull(source)) {
            throw new IllegalArgumentException("ZoneId properties cannot be null!");
        }

        return of(source);
    }
}
