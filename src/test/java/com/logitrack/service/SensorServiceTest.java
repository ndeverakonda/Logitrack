package com.logitrack.service;

import com.logitrack.BaseTest;
import com.logitrack.dao.AlertDao;
import com.logitrack.dao.SensorReadingDao;
import com.logitrack.model.SensorReading;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorServiceTest extends BaseTest {

    @Test
    void processReading_shouldSaveReading() {
        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        SensorReading reading = new SensorReading(
                null,
                101L,
                17.1,
                78.2,
                55.0,
                false,
                LocalDateTime.now()
        );

        sensorService.processReading(reading);

        List<Long> readings = jdbcTemplate.findMany(
                "SELECT sensor_id FROM sensor_reading",
                rs -> {
                    try {
                        return rs.getLong("sensor_id");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        assertEquals(1, readings.size());
        assertEquals(101L, readings.get(0));
    }

    @Test
    void validateReading_shouldThrow_whenReadingIsNull() {
        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        assertThrows(IllegalArgumentException.class, () -> sensorService.validateReading(null));
    }

    @Test
    void validateReading_shouldThrow_whenSensorIdIsNull() {
        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        SensorReading reading = new SensorReading(
                null,
                null,
                17.1,
                78.2,
                50.0,
                false,
                LocalDateTime.now()
        );

        assertThrows(IllegalArgumentException.class, () -> sensorService.validateReading(reading));
    }

    @Test
    void validateReading_shouldThrow_whenHumidityIsBelowZero() {
        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        SensorReading reading = new SensorReading(
                null,
                101L,
                17.1,
                78.2,
                -1.0,
                false,
                LocalDateTime.now()
        );

        assertThrows(IllegalArgumentException.class, () -> sensorService.validateReading(reading));
    }

    @Test
    void validateReading_shouldThrow_whenHumidityIsAboveHundred() {
        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        SensorReading reading = new SensorReading(
                null,
                101L,
                17.1,
                78.2,
                101.0,
                false,
                LocalDateTime.now()
        );

        assertThrows(IllegalArgumentException.class, () -> sensorService.validateReading(reading));
    }

    @Test
    void validateReading_shouldNotThrow_whenReadingIsValid() {
        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);
        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        SensorReading reading = new SensorReading(
                null,
                101L,
                17.1,
                78.2,
                50.0,
                false,
                LocalDateTime.now()
        );

        assertDoesNotThrow(() -> sensorService.validateReading(reading));
    }
}