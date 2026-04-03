package com.logitrack.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    void constructorAndGetters_shouldExposeValues() {
        LocalDateTime createdAt = LocalDateTime.now();
        Item item = new Item(10L, "Vaccine Case", 2L, createdAt);

        assertEquals(10L, item.getItemId());
        assertEquals("Vaccine Case", item.getItemName());
        assertEquals(2L, item.getWarehouseId());
        assertEquals(createdAt, item.getCreatedAt());
    }
}
