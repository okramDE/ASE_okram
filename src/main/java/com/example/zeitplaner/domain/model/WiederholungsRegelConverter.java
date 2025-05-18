package com.example.zeitplaner.domain.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WiederholungsRegelConverter
        implements AttributeConverter<WiederholungsRegel, String> {

    @Override
    public String convertToDatabaseColumn(WiederholungsRegel wr) {
        return wr == null ? null : wr.toRRuleString();
    }

    @Override
    public WiederholungsRegel convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isBlank()
                ? null
                : WiederholungsRegel.parse(dbData);
    }
}
