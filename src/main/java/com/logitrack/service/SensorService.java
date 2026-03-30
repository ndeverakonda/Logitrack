package com.logitrack.service;

import com.logitrack.dao.SensorReadingDao;
import com.logitrack.model.SensorReading;

public class SensorService {

    private final SensorReadingDao sensorReadingDao;
    private final AlertService alertService;

    public SensorService(SensorReadingDao sensorReadingDao, AlertService alertService) {
        this.sensorReadingDao = sensorReadingDao;
        this.alertService = alertService;
    }

    public void processReading(SensorReading reading) {
        validateReading(reading);
        sensorReadingDao.save(reading);
        alertService.checkAndGenerateAlert(reading);
    }

    public void validateReading(SensorReading reading) {
        if (reading == null) {
            throw new IllegalArgumentException("Sensor reading must not be null");
        }
        if (reading.getSensorId() == null) {
            throw new IllegalArgumentException("Sensor id must not be null");
        }
        if (reading.getHumidity() < 0 || reading.getHumidity() > 100) {
            throw new IllegalArgumentException("Humidity must be between 0 and 100");
        }
    }
}