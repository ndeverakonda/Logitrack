package com.logitrack.producer;

import com.logitrack.model.SensorReading;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SensorProducerTest {

    @Test
    void run_shouldProduceReadingBeforeStoppingOnInterrupt() throws Exception {
        BlockingQueue<SensorReading> queue = new ArrayBlockingQueue<>(2);
        Thread thread = new Thread(new SensorProducer(queue, 101L), "producer-test");

        thread.start();

        SensorReading reading = queue.poll(2, TimeUnit.SECONDS);
        assertNotNull(reading);
        assertTrue(reading.getHumidity() >= 50.0);
        assertTrue(reading.getHumidity() < 90.0);
        assertEqualsSensor(reading, 101L);

        thread.interrupt();
        thread.join(2000);

        assertFalse(thread.isAlive());
    }

    private void assertEqualsSensor(SensorReading reading, Long expectedSensorId) {
        assertEquals(expectedSensorId, reading.getSensorId());
        assertNotNull(reading.getRecordedAt());
    }
}
