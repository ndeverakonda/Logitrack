INSERT INTO warehouse (warehouse_id, location, capacity)
VALUES (1, 'Hyderabad Warehouse', 500);

INSERT INTO warehouse (warehouse_id, location, capacity)
VALUES (2, 'Bengaluru Warehouse', 350);

INSERT INTO item (item_id, item_name, warehouse_id)
VALUES (1001, 'MacBook Pro Batch A', 1);

INSERT INTO item (item_id, item_name, warehouse_id)
VALUES (1002, 'iPhone 15 Shipment X', 1);

INSERT INTO sensor (sensor_id, warehouse_id, status, last_active_at)
VALUES (101, 1, 'ACTIVE', CURRENT_TIMESTAMP);

INSERT INTO sensor (sensor_id, warehouse_id, status, last_active_at)
VALUES (102, 1, 'ACTIVE', CURRENT_TIMESTAMP);

INSERT INTO sensor_reading (reading_id, sensor_id, latitude, longitude, humidity, tamper_detected)
VALUES (1, 101, 17.3850, 78.4867, 68.5, FALSE);

INSERT INTO sensor_reading (reading_id, sensor_id, latitude, longitude, humidity, tamper_detected)
VALUES (2, 101, 17.3852, 78.4869, 72.5, TRUE);

INSERT INTO sensor_configuration (sensor_id, humidity_threshold, tamper_monitoring_enabled, reporting_interval_seconds)
VALUES (101, 70.0, TRUE, 300);

INSERT INTO sensor_configuration (sensor_id, humidity_threshold, tamper_monitoring_enabled, reporting_interval_seconds)
VALUES (102, 75.0, FALSE, 600);

INSERT INTO alert (sensor_id, reading_id, alert_type, message, severity)
VALUES (101, 1, 'HUMIDITY_HIGH', 'Humidity exceeded threshold', 'HIGH');

INSERT INTO alert (sensor_id, reading_id, alert_type, message, severity)
VALUES (101, 2, 'TAMPER_DETECTED', 'Tamper event detected', 'CRITICAL');

ALTER TABLE sensor_reading ALTER COLUMN reading_id RESTART WITH 3;
ALTER TABLE alert ALTER COLUMN alert_id RESTART WITH 3;
