package com.logitrack.producer;

import com.logitrack.model.SensorReading;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class SensorProducer implements Runnable {

    private final BlockingQueue<SensorReading> queue;
    private final Long sensorId;
    private final Random random = new Random();

    public SensorProducer(BlockingQueue<SensorReading> queue, Long sensorId) {
        this.queue = queue;
        this.sensorId = sensorId;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SensorReading reading = generateReading();
                queue.put(reading);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private SensorReading generateReading() {
        double latitude = 17.3850 + (random.nextDouble() / 100);
        double longitude = 78.4867 + (random.nextDouble() / 100);
        double humidity = 50 + (random.nextDouble() * 40);
        boolean tamperDetected = random.nextInt(10) == 0;

        return new SensorReading(
                null,
                sensorId,
                latitude,
                longitude,
                humidity,
                tamperDetected,
                LocalDateTime.now()
        );
    }
}