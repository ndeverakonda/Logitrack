package com.logitrack.service;

import com.logitrack.dao.AlertDao;
import com.logitrack.model.Alert;
import com.logitrack.model.SensorReading;

import java.time.LocalDateTime;

public class AlertService {

    private static final double DEFAULT_HUMIDITY_THRESHOLD = 70.0;

    private final AlertDao alertDao;

    public AlertService(AlertDao alertDao) {
        this.alertDao = alertDao;
    }

    public void checkAndGenerateAlert(SensorReading reading) {
        if (reading.getHumidity() > DEFAULT_HUMIDITY_THRESHOLD) {
            Alert humidityAlert = new Alert(
                    null,
                    reading.getSensorId(),
                    reading.getReadingId(),
                    "HUMIDITY_HIGH",
                    "Humidity exceeded configured threshold",
                    "HIGH",
                    LocalDateTime.now()
            );
            System.out.println("Generated humidity alert for sensor " + reading.getSensorId());
            alertDao.save(humidityAlert);
        }

        if (reading.isTamperDetected()) {
            Alert tamperAlert = new Alert(
                    null,
                    reading.getSensorId(),
                    reading.getReadingId(),
                    "TAMPER_ALERT",
                    "Tamper detected by sensor",
                    "CRITICAL",
                    LocalDateTime.now()
            );
            System.out.println("Generated tamper alert for sensor " + reading.getSensorId());
            alertDao.save(tamperAlert);
        }
    }
}