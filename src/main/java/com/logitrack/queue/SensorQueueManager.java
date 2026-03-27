package com.logitrack.queue;

import com.logitrack.model.SensorReading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SensorQueueManager {
    BlockingQueue<SensorReading> queue=new LinkedBlockingQueue<>(1000);

    public BlockingQueue<SensorReading> getQueue(){
        return this.queue;
    }
}
