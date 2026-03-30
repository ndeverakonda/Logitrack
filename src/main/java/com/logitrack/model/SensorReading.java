package com.logitrack.model;

import java.time.LocalDateTime;

public class SensorReading {

    private final Long readingId;
    private final Long sensorId;
    private final double latitude;
    private final double longitude;
    private final double humidity;
    private final boolean tamperDetected;
    private final LocalDateTime recordedAt;

    public SensorReading(Long readingId,
                         Long sensorId,
                         double latitude,
                         double longitude,
                         double humidity,
                         boolean tamperDetected,
                         LocalDateTime recordedAt) {
        this.readingId = readingId;
        this.sensorId = sensorId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.humidity = humidity;
        this.tamperDetected = tamperDetected;
        this.recordedAt = recordedAt;
    }

    public Long getReadingId() {
        return readingId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getHumidity() {
        return humidity;
    }

    public boolean isTamperDetected() {
        return tamperDetected;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
}