package com.simitchiyski.lockregistry.config.queue;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static java.time.LocalTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;

@Component
@ConfigurationPropertiesBinding
public class LocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(final String source) {
        if (isNull(source)) {
            throw new IllegalArgumentException("LocalTime properties cannot be null!");
        }

        return parse(source, ofPattern("HH:mm"));
    }
}
