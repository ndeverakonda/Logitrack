package com.logitrack.model;

import java.time.LocalDateTime;

public class Alert {

    private final Long alertId;
    private final Long sensorId;
    private final Long readingId;
    private final String alertType;
    private final String message;
    private final String severity;
    private final LocalDateTime createdAt;

    public Alert(Long alertId,
                 Long sensorId,
                 Long readingId,
                 String alertType,
                 String message,
                 String severity,
                 LocalDateTime createdAt) {
        this.alertId = alertId;
        this.sensorId = sensorId;
        this.readingId = readingId;
        this.alertType = alertType;
        this.message = message;
        this.severity = severity;
        this.createdAt = createdAt;
    }

    public Long getAlertId() {
        return alertId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public Long getReadingId() {
        return readingId;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getMessage() {
        return message;
    }

    public String getSeverity() {
        return severity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}