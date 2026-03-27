package com.logitrack.producer;

import com.logitrack.model.SensorReading;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class SensorProducer implements Runnable{
    String deviceId;
    String warehouseId;
    BlockingQueue<SensorReading> queue;
    Random random;

    public void run(){
        while (true) {
            //generate reading
            SensorReading randReading = generateReading();
            try {
                queue.put(randReading);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //log message
            System.out.println("Reading generated for Device ID: " + deviceId);

            //sleep
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //REPEAT

    }
    public SensorReading generateReading(){

        float latitude=random.nextFloat();
        float longitude=random.nextFloat();
        float humidity=random.nextFloat();
        boolean tamperDetected= random.nextBoolean();
        LocalDateTime timestamp=LocalDateTime.now();

        return new SensorReading(deviceId,warehouseId,latitude,longitude,humidity,tamperDetected,timestamp);
    }
}
