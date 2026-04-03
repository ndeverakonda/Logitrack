package com.logitrack.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlertTest {

    @Test
    void getters_shouldReturnExpectedValues() {
        LocalDateTime now = LocalDateTime.now();
        Alert alert = new Alert(1L, 101L, 5L, "HUMIDITY_HIGH", "msg", "HIGH", now);

        assertEquals(1L, alert.getAlertId());
        assertEquals(101L, alert.getSensorId());
        assertEquals(5L, alert.getReadingId());
        assertEquals("HUMIDITY_HIGH", alert.getAlertType());
        assertEquals("msg", alert.getMessage());
        assertEquals("HIGH", alert.getSeverity());
        assertEquals(now, alert.getCreatedAt());
    }
}