package com.logitrack.dao;

import com.logitrack.BaseTest;
import com.logitrack.model.Item;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemDaoTest extends BaseTest {

    @Test
    void save_shouldInsertItem() {
        ItemDao dao = new ItemDao(jdbcTemplate);
        Item item = new Item(1001L, "Medicine Box", 1L, LocalDateTime.now());

        dao.save(item);

        Item saved = dao.findById(1001L);
        assertNotNull(saved);
        assertEquals("Medicine Box", saved.getItemName());
        assertEquals(1L, saved.getWarehouseId());
    }

    @Test
    void findById_shouldReturnNull_whenItemDoesNotExist() {
        ItemDao dao = new ItemDao(jdbcTemplate);

        Item item = dao.findById(9999L);

        assertNull(item);
    }

    @Test
    void findAll_shouldReturnItemsInOrder() {
        ItemDao dao = new ItemDao(jdbcTemplate);
        dao.save(new Item(1001L, "Medicine Box", 1L, LocalDateTime.now()));
        dao.save(new Item(1002L, "Electronics Crate", 2L, LocalDateTime.now()));

        List<Item> items = dao.findAll();

        assertEquals(2, items.size());
        assertEquals(1001L, items.get(0).getItemId());
        assertEquals(1002L, items.get(1).getItemId());
    }
}
