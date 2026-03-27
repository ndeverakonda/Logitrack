package com.logitrack.model;

import java.time.LocalDateTime;

public class Alert {
    int alertId;
    String deviceId;
    String alertType; //ENUM?
    String message;
    String severity;
    LocalDateTime timestamp;

    public int getAlertId(){
        return this.alertId;
    }
    public String getDeviceId(){
        return this.deviceId;
    }
    public String getAlertType(){
        return this.alertType;
    }
    public String getMessage(){
        return this.message;
    }
    public String getSeverity(){
        return this.severity;
    }
    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

}
