package com.logitrack.model;

import java.time.LocalDateTime;

public class SensorConfiguration {

    private final Long sensorId;
    private final double humidityThreshold;
    private final boolean tamperMonitoringEnabled;
    private final int reportingIntervalSeconds;
    private final LocalDateTime updatedAt;

    public SensorConfiguration(Long sensorId,
                               double humidityThreshold,
                               boolean tamperMonitoringEnabled,
                               int reportingIntervalSeconds,
                               LocalDateTime updatedAt) {
        this.sensorId = sensorId;
        this.humidityThreshold = humidityThreshold;
        this.tamperMonitoringEnabled = tamperMonitoringEnabled;
        this.reportingIntervalSeconds = reportingIntervalSeconds;
        this.updatedAt = updatedAt;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public double getHumidityThreshold() {
        return humidityThreshold;
    }

    public boolean isTamperMonitoringEnabled() {
        return tamperMonitoringEnabled;
    }

    public int getReportingIntervalSeconds() {
        return reportingIntervalSeconds;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}