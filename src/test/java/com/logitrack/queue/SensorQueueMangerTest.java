package com.logitrack.queue;

import com.logitrack.model.SensorReading;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SensorQueueManagerTest {

    @Test
    void getQueue_shouldReturnQueueWithExpectedCapacityBehavior() throws Exception {
        SensorQueueManager manager = new SensorQueueManager(2);

        SensorReading reading1 = new SensorReading(null, 101L, 1.0, 1.0, 50.0, false, LocalDateTime.now());
        SensorReading reading2 = new SensorReading(null, 102L, 2.0, 2.0, 60.0, false, LocalDateTime.now());

        assertTrue(manager.getQueue().offer(reading1));
        assertTrue(manager.getQueue().offer(reading2));
        assertEquals(2, manager.getQueue().size());
    }
}