package com.logitrack.dao;

import com.logitrack.db.*;
import com.logitrack.model.SensorReading;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorReadingDaoTest {

    private static DataSource ds;
    private SensorReadingDao dao;

    @BeforeAll
    static void setup() {
        ds = TestDatabaseSupport.createDataSource();
        TestDatabaseSupport.runScript(ds, "db/schema.sql");
    }

    @BeforeEach
    void setUpTestData() {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(new DataSourceConnectionProvider(ds));
        jdbcTemplate.execute("DELETE FROM alert");
        jdbcTemplate.execute("DELETE FROM sensor_configuration");
        jdbcTemplate.execute("DELETE FROM sensor_reading");
        jdbcTemplate.execute("DELETE FROM sensor");
        jdbcTemplate.execute("DELETE FROM item");
        jdbcTemplate.execute("DELETE FROM warehouse");
        TestDatabaseSupport.runScript(ds, "db/test-data.sql");
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
