package com.siemens.csde.infrastructure.scheduler.config.web;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;


@Slf4j
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (Objects.isNull(source)) {
            return null;
        }

        String value = source.trim();
        try {
            return Date.from(Instant.parse(value));
        } catch (Exception ex) {
            log.error("date format conversion failed :{}", value);
            return null;
        }
    }
}