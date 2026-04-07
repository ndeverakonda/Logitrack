-- =========================================
-- SAMPLE DATA FOR LOGITRACK PRO
-- =========================================

-- -----------------------------------------
-- WAREHOUSES
-- -----------------------------------------
INSERT INTO warehouse VALUES
                          (1, 'Hyderabad Warehouse', 500, CURRENT_TIMESTAMP),
                          (2, 'Bengaluru Warehouse', 350, CURRENT_TIMESTAMP),
                          (3, 'Mumbai Warehouse', 600, CURRENT_TIMESTAMP);

-- -----------------------------------------
-- ITEMS
-- -----------------------------------------
INSERT INTO item VALUES
                     (1001, 'MacBook Pro Batch A', 1, CURRENT_TIMESTAMP),
                     (1002, 'iPhone 15 Shipment X', 1, CURRENT_TIMESTAMP),
                     (1003, 'Apple Watch Series 9', 1, CURRENT_TIMESTAMP),

                     (1004, 'Dell Latitude Batch B', 2, CURRENT_TIMESTAMP),
                     (1005, 'Samsung Galaxy S23', 2, CURRENT_TIMESTAMP),

                     (1006, 'iPad Air Shipment Z', 3, CURRENT_TIMESTAMP),
                     (1007, 'Surface Pro Devices', 3, CURRENT_TIMESTAMP),
                     (1008, 'Lenovo ThinkPad X1', 3, CURRENT_TIMESTAMP);

-- -----------------------------------------
-- SENSORS
-- -----------------------------------------
INSERT INTO sensor VALUES
                       (101, 1, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                       (102, 1, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                       (103, 2, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                       (104, 2, 'INACTIVE', CURRENT_TIMESTAMP - INTERVAL '2 hours', CURRENT_TIMESTAMP),
                       (105, 3, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                       (106, 3, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- -----------------------------------------
-- SENSOR CONFIG
-- -----------------------------------------
INSERT INTO sensor_configuration VALUES
                                     (101, 70.0, TRUE, 30, CURRENT_TIMESTAMP),
                                     (102, 65.0, TRUE, 45, CURRENT_TIMESTAMP),
                                     (103, 75.0, TRUE, 60, CURRENT_TIMESTAMP),
                                     (104, 80.0, FALSE, 120, CURRENT_TIMESTAMP),
                                     (105, 68.0, TRUE, 30, CURRENT_TIMESTAMP),
                                     (106, 72.0, TRUE, 30, CURRENT_TIMESTAMP);

-- -----------------------------------------
-- SENSOR-ITEM ASSIGNMENTS
-- -----------------------------------------
INSERT INTO sensor_item_assignment VALUES
                                       (101, 1001, CURRENT_TIMESTAMP),
                                       (102, 1002, CURRENT_TIMESTAMP),
                                       (103, 1004, CURRENT_TIMESTAMP),
                                       (104, 1005, CURRENT_TIMESTAMP),
                                       (105, 1006, CURRENT_TIMESTAMP),
                                       (106, 1007, CURRENT_TIMESTAMP);

-- -----------------------------------------
-- SENSOR READINGS (TIME SERIES)
-- -----------------------------------------
-- Hyderabad
INSERT INTO sensor_reading (
  sensor_id,
  latitude,
  longitude,
  humidity,
  tamper_detected,
  recorded_at
) VALUES
  (101, 17.3850, 78.4867, 65.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '30 min'),
  (101, 17.3851, 78.4868, 68.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '20 min'),
  (101, 17.3852, 78.4869, 72.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '10 min'),
  (101, 17.3853, 78.4870, 75.5, FALSE, CURRENT_TIMESTAMP - INTERVAL '5 min'),
  (102, 17.3854, 78.4871, 60.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '25 min'),
  (102, 17.3855, 78.4872, 62.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '15 min'),
  (102, 17.3856, 78.4873, 64.0, TRUE, CURRENT_TIMESTAMP - INTERVAL '8 min');

-- Bengaluru
INSERT INTO sensor_reading VALUES
  (DEFAULT, 103, 12.9716, 77.5946, 70.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '20 min'),
  (DEFAULT, 103, 12.9717, 77.5947, 78.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '10 min'),
  (DEFAULT, 103, 12.9718, 77.5948, 82.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '5 min'),

  (DEFAULT, 104, 12.9720, 77.5950, 55.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '1 hour');

-- Mumbai
INSERT INTO sensor_reading VALUES
  (DEFAULT, 105, 19.0760, 72.8777, 66.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '30 min'),
  (DEFAULT, 105, 19.0761, 72.8778, 69.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '15 min'),
  (DEFAULT, 105, 19.0762, 72.8779, 73.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '5 min'),

  (DEFAULT, 106, 19.0763, 72.8780, 60.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '20 min'),
  (DEFAULT, 106, 19.0764, 72.8781, 61.0, FALSE, CURRENT_TIMESTAMP - INTERVAL '10 min');

-- -----------------------------------------
-- ALERTS
-- -----------------------------------------
INSERT INTO alert (
  sensor_id,
  reading_id,
  alert_type,
  message,
  severity,
  created_at
) VALUES
  (
    101,
    3,
    'HUMIDITY_HIGH',
    'Humidity exceeded threshold (70%)',
    'HIGH',
    CURRENT_TIMESTAMP - INTERVAL '9 min'
  ),
  (
    101,
    4,
    'CRITICAL_HUMIDITY',
    'Humidity dangerously high (>75%)',
    'CRITICAL',
    CURRENT_TIMESTAMP - INTERVAL '4 min'
  ),
  (
    102,
    7,
    'TAMPER_ALERT',
    'Device tampering detected',
    'CRITICAL',
    CURRENT_TIMESTAMP - INTERVAL '7 min'
  ),
  (
    103,
    9,
    'HUMIDITY_HIGH',
    'Humidity exceeded threshold in Bengaluru warehouse',
    'HIGH',
    CURRENT_TIMESTAMP - INTERVAL '9 min'
  ),
  (
    103,
    10,
    'CRITICAL_HUMIDITY',
    'Humidity extremely high',
    'CRITICAL',
    CURRENT_TIMESTAMP - INTERVAL '4 min'
  ),
  (
    105,
    14,
    'HUMIDITY_HIGH',
    'Mumbai warehouse humidity alert',
    'HIGH',
    CURRENT_TIMESTAMP - INTERVAL '4 min'
  );
