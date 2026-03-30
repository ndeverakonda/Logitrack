package com.logitrack.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final Long warehouseId;
    private final String location;
    private final int capacity;
    private final LocalDateTime createdAt;
    private List<Item> items = new ArrayList<>();

    public Warehouse(Long warehouseId, String location, int capacity, LocalDateTime createdAt) {
        this.warehouseId = warehouseId;
        this.location = location;
        this.capacity = capacity;
        this.createdAt = createdAt;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}