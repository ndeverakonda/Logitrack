
-- =========================================================
-- 1. FULL CRUD SET
-- ENTITY: SENSOR
-- =========================================================

-- CREATE SENSOR
INSERT INTO sensor (
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
)
VALUES (
           201,
           1,
           'ACTIVE',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- READ SENSOR
SELECT
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
FROM sensor
WHERE sensor_id = 201;

-- UPDATE SENSOR
UPDATE sensor
SET status = 'MAINTENANCE',
    last_active_at = CURRENT_TIMESTAMP
WHERE sensor_id = 201;

-- DELETE SENSOR
DELETE FROM sensor
WHERE sensor_id = 201;


-- =========================================================
-- 2. SEARCH QUERY WITH DYNAMIC FILTERS, PAGINATION, SORTING
-- Use case: search items in warehouse inventory view
-- =========================================================

-- Example with:
-- warehouse filter = 1
-- item name filter = 'mac'
-- sort by created_at descending
-- pagination = first page

SELECT
    i.item_id,
    i.item_name,
    i.warehouse_id,
    i.created_at
FROM item i
WHERE
    (1 IS NULL OR i.warehouse_id = 1)
  AND ('mac' IS NULL OR LOWER(i.item_name) LIKE '%mac%'
ORDER BY i.created_at DESC
    LIMIT 10 OFFSET 0;

-- Example with:
-- no filters
-- sort alphabetically
-- second page

SELECT
    i.item_id,
    i.item_name,
    i.warehouse_id,
    i.created_at
FROM item i
WHERE
    (NULL IS NULL OR i.warehouse_id = NULL)
  AND (NULL IS NULL OR LOWER(i.item_name) LIKE '%mac%'
ORDER BY i.item_name ASC
    LIMIT 10 OFFSET 10;


-- =========================================================
-- 3. SEARCH QUERY WITH JOINED DATA
-- Use case: show item with warehouse and assigned sensors
-- =========================================================

SELECT
    i.item_id,
    i.item_name,
    w.warehouse_id,
    w.location AS warehouse_location,
    s.sensor_id,
    s.status AS sensor_status,
    sia.assigned_at
FROM item i
         JOIN warehouse w
              ON i.warehouse_id = w.warehouse_id
         LEFT JOIN sensor_item_assignment sia
                   ON i.item_id = sia.item_id
         LEFT JOIN sensor s
                   ON sia.sensor_id = s.sensor_id
ORDER BY i.item_id, s.sensor_id;

-- Joined alert view with warehouse and item context

SELECT
    a.alert_id,
    a.alert_type,
    a.message,
    a.severity,
    a.created_at,
    s.sensor_id,
    w.location AS warehouse_location,
    i.item_id,
    i.item_name,
    sr.humidity,
    sr.tamper_detected,
    sr.recorded_at
FROM alert a
         JOIN sensor s
              ON a.sensor_id = s.sensor_id
         JOIN warehouse w
              ON s.warehouse_id = w.warehouse_id
         LEFT JOIN sensor_item_assignment sia
                   ON s.sensor_id = sia.sensor_id
         LEFT JOIN item i
                   ON sia.item_id = i.item_id
         LEFT JOIN sensor_reading sr
                   ON a.reading_id = sr.reading_id
ORDER BY a.created_at DESC;


-- =========================================================
-- 4. STATISTIC QUERIES
-- =========================================================

-- Number of alerts per sensor

SELECT
    s.sensor_id,
    COUNT(a.alert_id) AS alert_count
FROM sensor s
         LEFT JOIN alert a
                   ON s.sensor_id = a.sensor_id
GROUP BY s.sensor_id
ORDER BY s.sensor_id;

-- Number of items per warehouse

SELECT
    w.warehouse_id,
    w.location,
    COUNT(i.item_id) AS item_count
FROM warehouse w
         LEFT JOIN item i
                   ON w.warehouse_id = i.warehouse_id
GROUP BY w.warehouse_id, w.location
ORDER BY w.warehouse_id;

-- Number of sensors assigned to each item

SELECT
    i.item_id,
    i.item_name,
    COUNT(sia.sensor_id) AS sensor_count
FROM item i
         LEFT JOIN sensor_item_assignment sia
                   ON i.item_id = sia.item_id
GROUP BY i.item_id, i.item_name
ORDER BY i.item_id;


-- =========================================================
-- 5. TOP-SOMETHING QUERIES
-- =========================================================

-- Top 5 sensors by number of alerts

SELECT
    s.sensor_id,
    COUNT(a.alert_id) AS alert_count
FROM sensor s
         LEFT JOIN alert a
                   ON s.sensor_id = a.sensor_id
GROUP BY s.sensor_id
ORDER BY alert_count DESC, s.sensor_id ASC
    LIMIT 5;

-- Top 5 items monitored by most sensors

SELECT
    i.item_id,
    i.item_name,
    COUNT(sia.sensor_id) AS sensor_count
FROM item i
         LEFT JOIN sensor_item_assignment sia
                   ON i.item_id = sia.item_id
GROUP BY i.item_id, i.item_name
ORDER BY sensor_count DESC, i.item_id ASC
    LIMIT 5;

-- Top 5 warehouses by number of alerts

SELECT
    w.warehouse_id,
    w.location,
    COUNT(a.alert_id) AS alert_count
FROM warehouse w
         LEFT JOIN sensor s
                   ON w.warehouse_id = s.warehouse_id
         LEFT JOIN alert a
                   ON s.sensor_id = a.sensor_id
GROUP BY w.warehouse_id, w.location
ORDER BY alert_count DESC, w.warehouse_id ASC
    LIMIT 5;


-- =========================================================
-- 6. QUERIES COVERING OOD / BUSINESS USE-CASES
-- =========================================================

-- Register warehouse

INSERT INTO warehouse (
    warehouse_id,
    location,
    capacity,
    created_at
)
VALUES (
           1,
           'Hyderabad Warehouse',
           500,
           CURRENT_TIMESTAMP
       );

INSERT INTO warehouse (
    warehouse_id,
    location,
    capacity,
    created_at
)
VALUES (
           2,
           'Bengaluru Warehouse',
           350,
           CURRENT_TIMESTAMP
       );

-- Add items

INSERT INTO item (
    item_id,
    item_name,
    warehouse_id,
    created_at
)
VALUES (
           1001,
           'MacBook Pro Batch A',
           1,
           CURRENT_TIMESTAMP
       );

INSERT INTO item (
    item_id,
    item_name,
    warehouse_id,
    created_at
)
VALUES (
           1002,
           'iPhone 15 Shipment X',
           1,
           CURRENT_TIMESTAMP
       );

INSERT INTO item (
    item_id,
    item_name,
    warehouse_id,
    created_at
)
VALUES (
           1003,
           'Dell Latitude Batch B',
           2,
           CURRENT_TIMESTAMP
       );

-- Register sensors

INSERT INTO sensor (
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
)
VALUES (
           101,
           1,
           'ACTIVE',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor (
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
)
VALUES (
           102,
           1,
           'ACTIVE',
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor (
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
)
VALUES (
           103,
           2,
           'INACTIVE',
           CURRENT_TIMESTAMP - INTERVAL '2 days',
           CURRENT_TIMESTAMP
       );

-- Configure sensors

INSERT INTO sensor_configuration (
    sensor_id,
    humidity_threshold,
    tamper_monitoring_enabled,
    reporting_interval_seconds,
    updated_at
)
VALUES (
           101,
           70.0,
           TRUE,
           30,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor_configuration (
    sensor_id,
    humidity_threshold,
    tamper_monitoring_enabled,
    reporting_interval_seconds,
    updated_at
)
VALUES (
           102,
           65.0,
           TRUE,
           60,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor_configuration (
    sensor_id,
    humidity_threshold,
    tamper_monitoring_enabled,
    reporting_interval_seconds,
    updated_at
)
VALUES (
           103,
           75.0,
           FALSE,
           120,
           CURRENT_TIMESTAMP
       );

-- Assign sensors to items

INSERT INTO sensor_item_assignment (
    sensor_id,
    item_id,
    assigned_at
)
VALUES (
           101,
           1001,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor_item_assignment (
    sensor_id,
    item_id,
    assigned_at
)
VALUES (
           102,
           1001,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor_item_assignment (
    sensor_id,
    item_id,
    assigned_at
)
VALUES (
           101,
           1002,
           CURRENT_TIMESTAMP
       );

INSERT INTO sensor_item_assignment (
    sensor_id,
    item_id,
    assigned_at
)
VALUES (
           103,
           1003,
           CURRENT_TIMESTAMP
       );

-- Insert sensor readings (core ingestion use-case)

INSERT INTO sensor_reading (
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
)
VALUES (
           101,
           17.3850,
           78.4867,
           68.5,
           FALSE,
           CURRENT_TIMESTAMP - INTERVAL '20 minutes'
       );

INSERT INTO sensor_reading (
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
)
VALUES (
           101,
           17.3852,
           78.4869,
           72.5,
           FALSE,
           CURRENT_TIMESTAMP - INTERVAL '10 minutes'
       );

INSERT INTO sensor_reading (
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
)
VALUES (
           102,
           17.3851,
           78.4868,
           60.0,
           TRUE,
           CURRENT_TIMESTAMP - INTERVAL '5 minutes'
       );

INSERT INTO sensor_reading (
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
)
VALUES (
           103,
           12.9716,
           77.5946,
           55.0,
           FALSE,
           CURRENT_TIMESTAMP - INTERVAL '1 day'
       );

-- Fetch sensor configuration for rule validation

SELECT
    sensor_id,
    humidity_threshold,
    tamper_monitoring_enabled,
    reporting_interval_seconds
FROM sensor_configuration
WHERE sensor_id = 101;

-- Fetch sensor details

SELECT
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
FROM sensor
WHERE sensor_id = 101;

-- Update sensor activity after reading ingestion

UPDATE sensor
SET last_active_at = CURRENT_TIMESTAMP,
    status = 'ACTIVE'
WHERE sensor_id = 101;

-- Insert alerts for abnormal conditions

INSERT INTO alert (
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
)
VALUES (
           101,
           2,
           'HUMIDITY_HIGH',
           'Humidity exceeded configured threshold',
           'HIGH',
           CURRENT_TIMESTAMP - INTERVAL '9 minutes'
       );

INSERT INTO alert (
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
)
VALUES (
           102,
           3,
           'TAMPER_ALERT',
           'Tamper detected by sensor',
           'CRITICAL',
           CURRENT_TIMESTAMP - INTERVAL '4 minutes'
       );

-- Fetch recent readings for one sensor

SELECT
    reading_id,
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
FROM sensor_reading
WHERE sensor_id = 101
ORDER BY recorded_at DESC
    LIMIT 10;

-- Fetch latest reading for one sensor

SELECT
    reading_id,
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
FROM sensor_reading
WHERE sensor_id = 101
ORDER BY recorded_at DESC
    LIMIT 1;

-- Fetch recent alerts

SELECT
    alert_id,
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
FROM alert
ORDER BY created_at DESC
    LIMIT 20;

-- Fetch alerts for one sensor

SELECT
    alert_id,
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
FROM alert
WHERE sensor_id = 101
ORDER BY created_at DESC;

-- Detect inactive sensors

SELECT
    sensor_id,
    warehouse_id,
    status,
    last_active_at
FROM sensor
WHERE last_active_at < CURRENT_TIMESTAMP - INTERVAL '1 day'
ORDER BY last_active_at ASC;

-- Remove sensor-item assignment

DELETE FROM sensor_item_assignment
WHERE sensor_id = 103
  AND item_id = 1003;


-- =========================================================
-- 7. ADDITIONAL DOMAIN QUERIES
-- =========================================================

-- Latest reading for each sensor

SELECT sr.*
FROM sensor_reading sr
         JOIN (
    SELECT
        sensor_id,
        MAX(recorded_at) AS latest_time
    FROM sensor_reading
    GROUP BY sensor_id
) latest
              ON sr.sensor_id = latest.sensor_id
                  AND sr.recorded_at = latest.latest_time
ORDER BY sr.sensor_id;

-- Readings exceeding humidity threshold

SELECT
    sr.reading_id,
    sr.sensor_id,
    sr.humidity,
    sc.humidity_threshold,
    sr.recorded_at
FROM sensor_reading sr
         JOIN sensor_configuration sc
              ON sr.sensor_id = sc.sensor_id
WHERE sr.humidity > sc.humidity_threshold
ORDER BY sr.recorded_at DESC;

-- Tamper events

SELECT
    reading_id,
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
FROM sensor_reading
WHERE tamper_detected = TRUE
ORDER BY recorded_at DESC;

-- View sensors with configuration

SELECT
    s.sensor_id,
    s.status,
    s.last_active_at,
    sc.humidity_threshold,
    sc.tamper_monitoring_enabled,
    sc.reporting_interval_seconds
FROM sensor s
         LEFT JOIN sensor_configuration sc
                   ON s.sensor_id = sc.sensor_id
ORDER BY s.sensor_id;