package com.logitrack.db;

import com.logitrack.BaseTest;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateTest extends BaseTest {

    @Test
    void execute_withVarargs_shouldInsertRow() {
        jdbcTemplate.execute(
                "INSERT INTO sensor_configuration (sensor_id, humidity_threshold, tamper_monitoring_enabled, reporting_interval_seconds, updated_at) VALUES (?, ?, ?, ?, ?)",
                101L, 70.0, true, 30, new Timestamp(System.currentTimeMillis())
        );

        Double threshold = jdbcTemplate.findOne(
                "SELECT humidity_threshold FROM sensor_configuration WHERE sensor_id = ?",
                rs -> getDouble(rs, "humidity_threshold"),
                101L
        );

        assertEquals(70.0, threshold);
    }

    @Test
    void execute_withConsumer_shouldInsertRow() {
        jdbcTemplate.execute(
                "INSERT INTO sensor_configuration (sensor_id, humidity_threshold, tamper_monitoring_enabled, reporting_interval_seconds, updated_at) VALUES (?, ?, ?, ?, ?)",
                ps -> {
                    try {
                        ps.setLong(1, 102L);
                        ps.setDouble(2, 65.0);
                        ps.setBoolean(3, true);
                        ps.setInt(4, 60);
                        ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    } catch (SQLException e) {
                        throw new DatabaseException("Failed to configure statement", e);
                    }
                }
        );

        Integer interval = jdbcTemplate.findOne(
                "SELECT reporting_interval_seconds FROM sensor_configuration WHERE sensor_id = ?",
                rs -> getInt(rs, "reporting_interval_seconds"),
                102L
        );

        assertEquals(60, interval);
    }

    @Test
    void execute_shouldThrowDatabaseException_onConstraintViolation() {
        assertThrows(DatabaseException.class, () ->
                jdbcTemplate.execute(
                        "INSERT INTO sensor (sensor_id, warehouse_id, status, last_active_at, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
                        101L, 1L, "ACTIVE"
                )
        );
    }

    @Test
    void findOne_shouldReturnNull_whenNoRowsFound() {
        Long result = jdbcTemplate.findOne(
                "SELECT sensor_id FROM sensor WHERE sensor_id = ?",
                rs -> getLong(rs, "sensor_id"),
                999L
        );

        assertNull(result);
    }

    @Test
    void findOne_shouldReturnMappedValue_whenExactlyOneRowExists() {
        String result = jdbcTemplate.findOne(
                "SELECT location FROM warehouse WHERE warehouse_id = ?",
                rs -> getString(rs, "location"),
                1L
        );

        assertEquals("Hyderabad Warehouse", result);
    }

    @Test
    void findOne_shouldThrowIncorrectResultSizeException_whenMoreThanOneRowExists() {
        assertThrows(IncorrectResultSizeException.class, () ->
                jdbcTemplate.findOne(
                        "SELECT location FROM warehouse",
                        rs -> getString(rs, "location")
                )
        );
    }

    @Test
    void findMany_shouldReturnEmptyList_whenNoRowsFound() {
        List<Long> result = jdbcTemplate.findMany(
                "SELECT sensor_id FROM sensor WHERE warehouse_id = ?",
                rs -> getLong(rs, "sensor_id"),
                999L
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findMany_shouldReturnAllMappedRows() {
        List<Long> result = jdbcTemplate.findMany(
                "SELECT sensor_id FROM sensor WHERE warehouse_id = ? ORDER BY sensor_id",
                rs -> getLong(rs, "sensor_id"),
                1L
        );

        assertEquals(2, result.size());
        assertEquals(101L, result.get(0));
        assertEquals(102L, result.get(1));
    }

    private static Long getLong(ResultSet rs, String column) {
        try {
            return rs.getLong(column);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer getInt(ResultSet rs, String column) {
        try {
            return rs.getInt(column);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Double getDouble(ResultSet rs, String column) {
        try {
            return rs.getDouble(column);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}