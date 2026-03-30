package com.logitrack.model;

import java.time.LocalDateTime;

public class Sensor {

    private final Long sensorId;
    private final Long warehouseId;
    private final String status;
    private final LocalDateTime lastActiveAt;
    private final LocalDateTime createdAt;

    public Sensor(Long sensorId,
                  Long warehouseId,
                  String status,
                  LocalDateTime lastActiveAt,
                  LocalDateTime createdAt) {
        this.sensorId = sensorId;
        this.warehouseId = warehouseId;
        this.status = status;
        this.lastActiveAt = lastActiveAt;
        this.createdAt = createdAt;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}