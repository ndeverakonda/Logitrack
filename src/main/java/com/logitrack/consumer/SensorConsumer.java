package com.logitrack.consumer;

import com.logitrack.model.SensorReading;
import com.logitrack.service.SensorService;

import java.util.concurrent.BlockingQueue;

public class SensorConsumer implements Runnable {

    private final BlockingQueue<SensorReading> queue;
    private final SensorService sensorService;

    public SensorConsumer(BlockingQueue<SensorReading> queue, SensorService sensorService) {
        this.queue = queue;
        this.sensorService = sensorService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SensorReading reading = queue.take();
                System.out.println(Thread.currentThread().getName()
                        + " consumed reading for sensor "
                        + reading.getSensorId());
                sensorService.processReading(reading);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}