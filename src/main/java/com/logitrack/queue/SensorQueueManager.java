package com.logitrack.queue;

import com.logitrack.model.SensorReading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SensorQueueManager {

    private final BlockingQueue<SensorReading> queue;

    public SensorQueueManager(int capacity) {
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    public BlockingQueue<SensorReading> getQueue() {
        return queue;
    }
}