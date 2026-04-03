package com.logitrack.dao;

import com.logitrack.BaseTest;
import com.logitrack.model.Warehouse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDaoTest extends BaseTest {

    @Test
    void save_shouldInsertWarehouse() {
        WarehouseDao dao = new WarehouseDao(jdbcTemplate, new ItemDao(jdbcTemplate));

        Warehouse warehouse = new Warehouse(
                3L,
                "Mumbai Warehouse",
                700,
                LocalDateTime.now()
        );

        dao.save(warehouse);

        Warehouse saved = dao.findById(3L);
        assertNotNull(saved);
        assertEquals("Mumbai Warehouse", saved.getLocation());
    }

    @Test
    void findById_shouldReturnWarehouse_whenExists() {
        WarehouseDao dao = new WarehouseDao(jdbcTemplate, new ItemDao(jdbcTemplate));

        Warehouse warehouse = dao.findById(1L);

        assertNotNull(warehouse);
        assertEquals("Hyderabad Warehouse", warehouse.getLocation());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {
        WarehouseDao dao = new WarehouseDao(jdbcTemplate, new ItemDao(jdbcTemplate));

        Warehouse warehouse = dao.findById(999L);

        assertNull(warehouse);
    }

    @Test
    void findAll_shouldReturnAllWarehouses() {
        WarehouseDao dao = new WarehouseDao(jdbcTemplate, new ItemDao(jdbcTemplate));

        List<Warehouse> warehouses = dao.findAll();

        assertEquals(2, warehouses.size());
    }
}
