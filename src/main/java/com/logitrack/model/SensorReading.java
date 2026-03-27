package com.logitrack.model;

import java.time.LocalDateTime;
import java.util.Date;

public class SensorReading {
    String deviceId;
    String warehouseId;

    float latitude;
    float longitude;
    float humidity;
    boolean tamperDetected;
    LocalDateTime timestamp;

    public SensorReading(String deviceId, String warehouseId, float latitude, float longitude, float humidity, boolean tamperDetected, LocalDateTime timestamp) {
        this.deviceId=deviceId;
        this.warehouseId=warehouseId;
        this.latitude=latitude;
        this.longitude=longitude;
        this.humidity=humidity;
        this.tamperDetected=tamperDetected;
        this.timestamp=timestamp;
    }

    public String getDeviceId(){
        return this.deviceId;
    }
    public String getWarehouseId(){
        return this.warehouseId;
    }
    public float getLatitude(){
        return this.latitude;
    }
    public float getLongitude(){
        return this.longitude;
    }
    public float getHumidity(){
return this.humidity;
    }
    public boolean isTamperDetected(){
        return this.tamperDetected;
    }
    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }


}


