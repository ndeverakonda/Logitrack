package com.logitrack.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SensorReadingTest {

    @Test
    void getters_shouldReturnExpectedValues() {
        LocalDateTime now = LocalDateTime.now();
        SensorReading reading = new SensorReading(1L, 101L, 17.0, 78.0, 55.0, true, now);

        assertEquals(1L, reading.getReadingId());
        assertEquals(101L, reading.getSensorId());
        assertEquals(17.0, reading.getLatitude());
        assertEquals(78.0, reading.getLongitude());
        assertEquals(55.0, reading.getHumidity());
        assertTrue(reading.isTamperDetected());
        assertEquals(now, reading.getRecordedAt());
    }
}