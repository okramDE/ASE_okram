package com.example.zeitplaner.domain.repository.model;

import com.example.zeitplaner.domain.model.WiederholungsRegel;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

class WiederholungsRegelTest {

    @Test
    void parse_fullRule() {
        String rule = "FREQ=DAILY;INTERVAL=2;COUNT=3;" +
                "UNTIL=20250601T000000Z;BYDAY=MO,WE;BYMONTHDAY=1,15";
        var wr = WiederholungsRegel.parse(rule);
        assertThat(wr.getFrequency()).isEqualTo(WiederholungsRegel.Frequency.DAILY);
        assertThat(wr.getInterval()).isEqualTo(2);
        assertThat(wr.getCount()).isEqualTo(3);
        assertThat(wr.getUntil()).isEqualTo(
                LocalDateTime.of(2025,6,1,0,0));
        assertThat(wr.getByDay()).containsExactly("MO","WE");
        assertThat(wr.getByMonthDay()).containsExactly(1,15);
    }

    @Test
    void parse_invalidPart_throws() {
        assertThatThrownBy(() ->
                WiederholungsRegel.parse("FREQX=DAILY"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}