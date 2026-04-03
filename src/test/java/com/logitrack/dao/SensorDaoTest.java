package com.logitrack.dao;

import com.logitrack.BaseTest;
import com.logitrack.model.Sensor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorDaoTest extends BaseTest {

    @Test
    void save_shouldInsertSensor() {
        SensorDao dao = new SensorDao(jdbcTemplate);

        Sensor sensor = new Sensor(
                200L,
                1L,
                "ACTIVE",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        dao.save(sensor);

        Sensor saved = dao.findById(200L);
        assertNotNull(saved);
        assertEquals("ACTIVE", saved.getStatus());
    }

    @Test
    void findById_shouldReturnSensor_whenExists() {
        SensorDao dao = new SensorDao(jdbcTemplate);

        Sensor sensor = dao.findById(101L);

        assertNotNull(sensor);
        assertEquals(101L, sensor.getSensorId());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        SensorDao dao = new SensorDao(jdbcTemplate);

        Sensor sensor = dao.findById(999L);

        assertNull(sensor);
    }

    @Test
    void findAll_shouldReturnAllSensors() {
        SensorDao dao = new SensorDao(jdbcTemplate);

        List<Sensor> sensors = dao.findAll();

        assertEquals(3, sensors.size());
    }
}