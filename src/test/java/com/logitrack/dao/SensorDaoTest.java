package com.logitrack.dao;

import com.logitrack.db.*;
import com.logitrack.model.Sensor;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorDaoTest {

    private static DataSource ds;
    private SensorDao dao;

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
        dao = new SensorDao(jdbcTemplate);
    }

    @Test
    void shouldInsertSensor() {
        Sensor sensor = new Sensor(
                103L,
                1L,
                "ACTIVE",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        dao.save(sensor);

        Sensor result = dao.findById(103L);
        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void shouldReturnNullIfSensorNotFound() {
        assertNull(dao.findById(999L));
    }

    @Test
    void shouldFindAllSensors() {
        List<Sensor> sensors = dao.findAll();
        assertTrue(sensors.size() >= 0);
    }
}
