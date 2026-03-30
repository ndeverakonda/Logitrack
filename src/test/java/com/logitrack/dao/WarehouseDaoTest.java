package com.logitrack.dao;

import com.logitrack.db.*;
import com.logitrack.model.Warehouse;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDaoTest {

    private static DataSource ds;
    private WarehouseDao dao;

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
        ItemDao itemDao = new ItemDao(jdbcTemplate);
        dao = new WarehouseDao(jdbcTemplate, itemDao);
    }

    @Test
    void shouldFindById() {
        Warehouse w = dao.findById(1L);
        assertNotNull(w);
        assertEquals("Hyderabad Warehouse", w.getLocation());
    }

    @Test
    void shouldReturnNullIfNotFound() {
        assertNull(dao.findById(999L));
    }

    @Test
    void shouldFindAll() {
        List<Warehouse> list = dao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void shouldInsertWarehouse() {
        Warehouse w = new Warehouse(3L, "Chennai", 150, LocalDateTime.now());
        dao.save(w);

        assertNotNull(dao.findById(3L));
    }
}
