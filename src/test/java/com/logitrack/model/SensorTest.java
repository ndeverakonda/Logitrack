package com.logitrack.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {

    @Test
    void getters_shouldReturnExpectedValues() {
        LocalDateTime now = LocalDateTime.now();
        Sensor sensor = new Sensor(101L, 1L, "ACTIVE", now, now);

        assertEquals(101L, sensor.getSensorId());
        assertEquals(1L, sensor.getWarehouseId());
        assertEquals("ACTIVE", sensor.getStatus());
        assertEquals(now, sensor.getLastActiveAt());
        assertEquals(now, sensor.getCreatedAt());
    }
}