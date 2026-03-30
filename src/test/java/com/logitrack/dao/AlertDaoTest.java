package com.logitrack.dao;

import com.logitrack.db.DataSourceConnectionProvider;
import com.logitrack.db.JdbcTemplate;
import com.logitrack.db.TestDatabaseSupport;
import com.logitrack.model.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertDaoTest {

    private DataSource dataSource;
    private AlertDao alertDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        dataSource = TestDatabaseSupport.createDataSource();
        TestDatabaseSupport.runScript(dataSource, "db/schema.sql");
        jdbcTemplate = new JdbcTemplate(new DataSourceConnectionProvider(dataSource));
        TestDatabaseSupport.runScript(dataSource, "db/test-data.sql");
        alertDao = new AlertDao(jdbcTemplate);
    }

    @Test
    void save_shouldInsertAlert() {
        Alert alert = new Alert(
                null,
                101L,
                1L,
                "TEST_ALERT",
                "Test alert message",
                "MEDIUM",
                LocalDateTime.now()
        );

        alertDao.save(alert);

        List<Alert> alerts = alertDao.findAll();
        assertEquals(3, alerts.size());
    }

    @Test
    void findById_shouldReturnAlert_whenExists() {
        Alert alert = alertDao.findById(1L);

        assertNotNull(alert);
        assertEquals(101L, alert.getSensorId());
        assertEquals("HUMIDITY_HIGH", alert.getAlertType());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        Alert alert = alertDao.findById(999L);

        assertNull(alert);
    }

    @Test
    void findAll_shouldReturnAllAlerts() {
        List<Alert> alerts = alertDao.findAll();

        assertEquals(2, alerts.size());
    }

    @Test
    void save_shouldThrowException_whenSensorDoesNotExist() {
        Alert alert = new Alert(
                null,
                999L,
                null,
                "INVALID_ALERT",
                "Invalid sensor",
                "LOW",
                LocalDateTime.now()
        );

        assertThrows(RuntimeException.class, () -> alertDao.save(alert));
    }
}
