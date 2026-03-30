package com.logitrack.model;

import java.time.LocalDateTime;

public class Item {

    private final Long itemId;
    private final String itemName;
    private final Long warehouseId;
    private final LocalDateTime createdAt;

    public Item(Long itemId, String itemName, Long warehouseId, LocalDateTime createdAt) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.warehouseId = warehouseId;
        this.createdAt = createdAt;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}