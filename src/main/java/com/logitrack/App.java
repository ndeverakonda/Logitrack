package com.logitrack;

import com.logitrack.consumer.SensorConsumer;
import com.logitrack.dao.AlertDao;
import com.logitrack.dao.SensorReadingDao;
import com.logitrack.db.ConnectionProvider;
import com.logitrack.db.DriverManagerConnectionProvider;
import com.logitrack.db.JdbcTemplate;
import com.logitrack.producer.SensorProducer;
import com.logitrack.queue.SensorQueueManager;
import com.logitrack.service.AlertService;
import com.logitrack.service.SensorService;

public class App {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/logitrack";
        String username = "postgres";
        String password = "postgres";

        ConnectionProvider connectionProvider =
                new DriverManagerConnectionProvider(url, username, password);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionProvider);

        SensorReadingDao sensorReadingDao = new SensorReadingDao(jdbcTemplate);
        AlertDao alertDao = new AlertDao(jdbcTemplate);

        AlertService alertService = new AlertService(alertDao);
        SensorService sensorService = new SensorService(sensorReadingDao, alertService);

        SensorQueueManager queueManager = new SensorQueueManager(100);

        Thread producer1 = new Thread(
                new SensorProducer(queueManager.getQueue(), 101L),
                "producer-101"
        );
        Thread producer2 = new Thread(
                new SensorProducer(queueManager.getQueue(), 102L),
                "producer-102"
        );

        Thread consumer1 = new Thread(
                new SensorConsumer(queueManager.getQueue(), sensorService),
                "consumer-1"
        );
        Thread consumer2 = new Thread(
                new SensorConsumer(queueManager.getQueue(), sensorService),
                "consumer-2"
        );

        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();
    }
}