package com.logitrack.model;

import java.time.LocalDateTime;

public class Device {
    String deviceId;
    String warehouseId;
    String status; //ENUM?
    LocalDateTime lastActive;

    public String getDeviceId(){
        return this.deviceId;
    }
    public String getWarehouseId(){
        return this.warehouseId;
    }
    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public LocalDateTime getLastActive(){
        return this.lastActive;
    }
}


