package com.logitrack.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    void getters_shouldReturnExpectedValues() {
        LocalDateTime now = LocalDateTime.now();
        Warehouse warehouse = new Warehouse(1L, "Hyderabad", 500, now);

        assertEquals(1L, warehouse.getWarehouseId());
        assertEquals("Hyderabad", warehouse.getLocation());
        assertEquals(500, warehouse.getCapacity());
        assertEquals(now, warehouse.getCreatedAt());
    }
}