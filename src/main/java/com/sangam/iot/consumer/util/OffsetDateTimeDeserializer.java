package com.sangam.iot.consumer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    private static final DateTimeFormatter LOCAL_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (text == null || text.isBlank()) {
            return null;
        }

        try {
            // try to parse with offset first (e.g. 2025-12-30T21:21:43Z or +00:00)
            return OffsetDateTime.parse(text);
        } catch (DateTimeParseException ignored) {
            // fallback: parse as local date-time and attach UTC offset (change if you prefer another default)
            try {
                LocalDateTime ldt = LocalDateTime.parse(text, LOCAL_FORMATTER);
                return ldt.atOffset(ZoneOffset.UTC);
            } catch (DateTimeException ex) {
                throw JsonMappingException.from(p, "Cannot parse OffsetDateTime: " + text, ex);
            }
        }
    }
}
