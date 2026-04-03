package com.logitrack.service;

import com.logitrack.BaseTest;
import com.logitrack.dao.AlertDao;
import com.logitrack.model.SensorReading;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertServiceTest extends BaseTest {

    private void insertReading(Long readingId,
                               Long sensorId,
                               double latitude,
                               double longitude,
                               double humidity,
                               boolean tamperDetected,
                               LocalDateTime recordedAt) {
        jdbcTemplate.execute(
                """
                INSERT INTO sensor_reading
                (reading_id, sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """,
                readingId,
                sensorId,
                latitude,
                longitude,
                humidity,
                tamperDetected,
                java.sql.Timestamp.valueOf(recordedAt)
        );
    }

    @Test
    void checkAndGenerateAlert_shouldCreateHumidityAlert_whenHumidityIsHigh() {
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService service = new AlertService(alertDao);
        LocalDateTime recordedAt = LocalDateTime.now();

        SensorReading reading = new SensorReading(
                1L,
                101L,
                17.0,
                78.0,
                90.0,
                false,
                recordedAt
        );
        insertReading(1L, 101L, 17.0, 78.0, 90.0, false, recordedAt);

        service.checkAndGenerateAlert(reading);

        List<String> alerts = jdbcTemplate.findMany(
                "SELECT alert_type FROM alert",
                rs -> {
                    try {
                        return rs.getString("alert_type");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertEquals(1, alerts.size());
        assertEquals("HUMIDITY_HIGH", alerts.get(0));
    }

    @Test
    void checkAndGenerateAlert_shouldCreateTamperAlert_whenTamperDetected() {
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService service = new AlertService(alertDao);
        LocalDateTime recordedAt = LocalDateTime.now();

        SensorReading reading = new SensorReading(
                1L,
                101L,
                17.0,
                78.0,
                40.0,
                true,
                recordedAt
        );
        insertReading(1L, 101L, 17.0, 78.0, 40.0, true, recordedAt);

        service.checkAndGenerateAlert(reading);

        List<String> alerts = jdbcTemplate.findMany(
                "SELECT alert_type FROM alert",
                rs -> {
                    try {
                        return rs.getString("alert_type");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertEquals(1, alerts.size());
        assertEquals("TAMPER_ALERT", alerts.get(0));
    }

    @Test
    void checkAndGenerateAlert_shouldCreateTwoAlerts_whenBothConditionsAreTrue() {
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService service = new AlertService(alertDao);
        LocalDateTime recordedAt = LocalDateTime.now();

        SensorReading reading = new SensorReading(
                1L,
                101L,
                17.0,
                78.0,
                95.0,
                true,
                recordedAt
        );
        insertReading(1L, 101L, 17.0, 78.0, 95.0, true, recordedAt);

        service.checkAndGenerateAlert(reading);

        List<String> alerts = jdbcTemplate.findMany(
                "SELECT alert_type FROM alert ORDER BY alert_type",
                rs -> {
                    try {
                        return rs.getString("alert_type");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertEquals(2, alerts.size());
    }

    @Test
    void checkAndGenerateAlert_shouldCreateNoAlert_whenConditionsAreNormal() {
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService service = new AlertService(alertDao);
        LocalDateTime recordedAt = LocalDateTime.now();

        SensorReading reading = new SensorReading(
                1L,
                101L,
                17.0,
                78.0,
                50.0,
                false,
                recordedAt
        );
        insertReading(1L, 101L, 17.0, 78.0, 50.0, false, recordedAt);

        service.checkAndGenerateAlert(reading);

        List<String> alerts = jdbcTemplate.findMany(
                "SELECT alert_type FROM alert",
                rs -> {
                    try {
                        return rs.getString("alert_type");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertTrue(alerts.isEmpty());
    }
}
