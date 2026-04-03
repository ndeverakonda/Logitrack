package com.logitrack.dao;

import com.logitrack.BaseTest;
import com.logitrack.model.Alert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertDaoTest extends BaseTest {

    @Test
    void save_shouldInsertAlert() {
        AlertDao dao = new AlertDao(jdbcTemplate);

        jdbcTemplate.execute("""
                INSERT INTO sensor_reading (sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                """, 101L, 17.0, 78.0, 82.0, false);

        Alert alert = new Alert(
                null,
                101L,
                1L,
                "HUMIDITY_HIGH",
                "Humidity exceeded threshold",
                "HIGH",
                LocalDateTime.now()
        );

        dao.save(alert);

        List<Alert> alerts = dao.findAll();
        assertEquals(1, alerts.size());
        assertEquals("HUMIDITY_HIGH", alerts.get(0).getAlertType());
    }

    @Test
    void findById_shouldReturnAlert_whenExists() {
        AlertDao dao = new AlertDao(jdbcTemplate);

        jdbcTemplate.execute("""
                INSERT INTO sensor_reading (sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                """, 101L, 17.0, 78.0, 82.0, false);

        jdbcTemplate.execute("""
                INSERT INTO alert (sensor_id, reading_id, alert_type, message, severity, created_at)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
                """, 101L, 1L, "HUMIDITY_HIGH", "Humidity exceeded threshold", "HIGH");

        Alert alert = dao.findById(1L);

        assertNotNull(alert);
        assertEquals("HUMIDITY_HIGH", alert.getAlertType());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        AlertDao dao = new AlertDao(jdbcTemplate);

        Alert alert = dao.findById(999L);

        assertNull(alert);
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoAlertsExist() {
        AlertDao dao = new AlertDao(jdbcTemplate);

        List<Alert> alerts = dao.findAll();

        assertNotNull(alerts);
        assertTrue(alerts.isEmpty());
    }

    @Test
    void save_shouldThrowException_whenSensorDoesNotExist() {
        AlertDao dao = new AlertDao(jdbcTemplate);

        Alert alert = new Alert(
                null,
                999L,
                null,
                "INVALID",
                "bad sensor",
                "LOW",
                LocalDateTime.now()
        );

        assertThrows(RuntimeException.class, () -> dao.save(alert));
    }
}