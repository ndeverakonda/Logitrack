package com.logitrack.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateTest {

    private static DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void beforeAll() {
        dataSource = TestDatabaseSupport.createDataSource();
        TestDatabaseSupport.runScript(dataSource, "db/schema.sql");
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate(new DataSourceConnectionProvider(dataSource));

        jdbcTemplate.execute("DELETE FROM alert");
        jdbcTemplate.execute("DELETE FROM sensor_configuration");
        jdbcTemplate.execute("DELETE FROM sensor_reading");
        jdbcTemplate.execute("DELETE FROM sensor");
        jdbcTemplate.execute("DELETE FROM item");
        jdbcTemplate.execute("DELETE FROM warehouse");

        TestDatabaseSupport.runScript(dataSource, "db/test-data.sql");
    }

    @Test
    void execute_shouldInsertRow_withVarargs() {
        jdbcTemplate.execute(
                "INSERT INTO warehouse (warehouse_id, location, capacity) VALUES (?, ?, ?)",
                3L, "Chennai Warehouse", 200
        );

        Long id = jdbcTemplate.findOne(
                "SELECT warehouse_id FROM warehouse WHERE warehouse_id = ?",
                rs -> getLong(rs, "warehouse_id"),
                3L
        );

        assertEquals(3L, id);
    }

    @Test
    void execute_shouldInsertRow_withConsumer() {
        jdbcTemplate.execute(
                "INSERT INTO warehouse (warehouse_id, location, capacity) VALUES (?, ?, ?)",
                ps -> {
                    try {
                        ps.setLong(1, 4L);
                        ps.setString(2, "Pune Warehouse");
                        ps.setInt(3, 150);
                    } catch (SQLException e) {
                        throw new DatabaseException("Statement configuration failed", e);
                    }
                }
        );

        Integer count = jdbcTemplate.findOne(
                "SELECT COUNT(*) AS cnt FROM warehouse WHERE warehouse_id = ?",
                rs -> getInt(rs, "cnt"),
                4L
        );

        assertEquals(1, count);
    }

    @Test
    void findOne_shouldReturnNull_whenNoRowsFound() {
        String result = jdbcTemplate.findOne(
                "SELECT location FROM warehouse WHERE warehouse_id = ?",
                rs -> getString(rs, "location"),
                999L
        );

        assertNull(result);
    }

    @Test
    void findOne_shouldReturnMappedObject_whenExactlyOneRowExists() {
        String result = jdbcTemplate.findOne(
                "SELECT location FROM warehouse WHERE warehouse_id = ?",
                rs -> getString(rs, "location"),
                1L
        );

        assertEquals("Hyderabad Warehouse", result);
    }

    @Test
    void findOne_shouldThrow_whenMoreThanOneRowExists() {
        assertThrows(IncorrectResultSizeException.class, () ->
                jdbcTemplate.findOne(
                        "SELECT location FROM warehouse",
                        rs -> getString(rs, "location")
                )
        );
    }

    @Test
    void findMany_shouldReturnEmptyList_whenNoRowsFound() {
        List<String> results = jdbcTemplate.findMany(
                "SELECT location FROM warehouse WHERE capacity > ?",
                rs -> getString(rs, "location"),
                9999
        );

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void findMany_shouldReturnAllMappedRows() {
        List<String> results = jdbcTemplate.findMany(
                "SELECT location FROM warehouse ORDER BY warehouse_id",
                rs -> getString(rs, "location")
        );

        assertEquals(2, results.size());
        assertEquals("Hyderabad Warehouse", results.get(0));
        assertEquals("Bengaluru Warehouse", results.get(1));
    }

    private static String getString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
