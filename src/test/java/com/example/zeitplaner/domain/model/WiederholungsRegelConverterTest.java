package com.example.zeitplaner.domain.model;

import com.example.zeitplaner.domain.model.WiederholungsRegelConverter;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class WiederholungsRegelConverterTest {

    private final WiederholungsRegelConverter conv = new WiederholungsRegelConverter();

    @Test
    void convert_roundTrip() {
        String rule = "FREQ=WEEKLY;COUNT=2";
        var wr = conv.convertToEntityAttribute(rule);
        String back = conv.convertToDatabaseColumn(wr);
        assertThat(back).isEqualTo(rule);
    }

    @Test
    void convert_nulls() {
        assertThat(conv.convertToEntityAttribute(null)).isNull();
        assertThat(conv.convertToDatabaseColumn(null)).isNull();
    }
}