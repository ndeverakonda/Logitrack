package com.logitrack.consumer;

import com.logitrack.BaseTest;
import com.logitrack.dao.AlertDao;
import com.logitrack.dao.SensorReadingDao;
import com.logitrack.model.SensorReading;
import com.logitrack.service.AlertService;
import com.logitrack.service.SensorService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SensorConsumerTest extends BaseTest {

    @Test
    void run_shouldConsumeReadingAndStopWhenInterrupted() throws Exception {
        BlockingQueue<SensorReading> queue = new ArrayBlockingQueue<>(2);
        SensorService sensorService = new SensorService(
                new SensorReadingDao(jdbcTemplate),
                new AlertService(new AlertDao(jdbcTemplate))
        );
        Thread thread = new Thread(new SensorConsumer(queue, sensorService), "consumer-test");

        queue.put(new SensorReading(null, 101L, 17.1, 78.2, 88.0, false, LocalDateTime.now()));
        thread.start();

        waitForCount("SELECT COUNT(*) FROM sensor_reading", 1);
        waitForCount("SELECT COUNT(*) FROM alert", 1);

        thread.interrupt();
        thread.join(2000);

        assertFalse(thread.isAlive());
        assertEquals(1L, count("SELECT COUNT(*) FROM sensor_reading"));
        assertEquals(1L, count("SELECT COUNT(*) FROM alert"));
    }

    @Test
    void run_shouldContinueAfterRuntimeExceptionAndStopWhenInterrupted() throws Exception {
        BlockingQueue<SensorReading> queue = new ArrayBlockingQueue<>(2);
        SensorService failingService = new SensorService(
                new SensorReadingDao(jdbcTemplate),
                new AlertService(new AlertDao(jdbcTemplate))
        ) {
            @Override
            public void processReading(SensorReading reading) {
                throw new RuntimeException("boom");
            }
        };
        Thread thread = new Thread(new SensorConsumer(queue, failingService), "consumer-error-test");

        queue.put(new SensorReading(null, 101L, 17.1, 78.2, 55.0, false, LocalDateTime.now()));
        thread.start();

        waitForQueueToDrain(queue);
        thread.interrupt();
        thread.join(2000);

        assertFalse(thread.isAlive());
        assertEquals(0L, count("SELECT COUNT(*) FROM sensor_reading"));
    }

    private void waitForCount(String sql, long expected) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 2000;
        while (System.currentTimeMillis() < deadline) {
            if (count(sql) == expected) {
                return;
            }
            Thread.sleep(25);
        }
        assertEquals(expected, count(sql));
    }

    private void waitForQueueToDrain(BlockingQueue<SensorReading> queue) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 2000;
        while (System.currentTimeMillis() < deadline) {
            if (queue.isEmpty()) {
                return;
            }
            Thread.sleep(25);
        }
        assertTrue(queue.isEmpty());
    }

    private long count(String sql) {
        Long result = jdbcTemplate.findOne(sql, rs -> {
            try {
                return rs.getLong(1);
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return result == null ? 0L : result;
    }
}
