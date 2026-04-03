package com.logitrack.dao;

import com.logitrack.BaseTest;
import com.logitrack.model.SensorReading;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorReadingDaoTest extends BaseTest {

    private SensorReadingDao dao;

    @BeforeEach
    void setUpTestData() {
        dao = new SensorReadingDao(jdbcTemplate);
    }

    @Test
    void shouldInsertReading() {
        SensorReading reading = new SensorReading(
                null,
                101L,
                17.3850,
                78.4867,
                65.5,
                false,
                LocalDateTime.now()
        );

        dao.save(reading);

        List<SensorReading> readings = dao.findBySensorId(101L);
        assertFalse(readings.isEmpty());
    }

    @Test
    void shouldReturnEmptyIfNoReadings() {
        List<SensorReading> readings = dao.findBySensorId(999L);
        assertTrue(readings.isEmpty());
    }
}
