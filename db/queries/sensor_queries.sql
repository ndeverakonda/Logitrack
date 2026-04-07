-- CREATE SENSOR
INSERT INTO sensor (
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
)
VALUES (?, ?, ?, ?, ?);

-- READ SENSOR BY ID
SELECT
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
FROM sensor
WHERE sensor_id = ?;

-- READ ALL SENSORS
SELECT
    sensor_id,
    warehouse_id,
    status,
    last_active_at,
    created_at
FROM sensor
ORDER BY sensor_id;

-- UPDATE SENSOR STATUS
UPDATE sensor
SET status = ?,
    last_active_at = ?
WHERE sensor_id = ?;

-- DELETE SENSOR
DELETE FROM sensor
WHERE sensor_id = ?;

-- INSERT SENSOR CONFIGURATION
INSERT INTO sensor_configuration (
    sensor_id,
    humidity_threshold,
    tamper_monitoring_enabled,
    reporting_interval_seconds,
    updated_at
)
VALUES (?, ?, ?, ?, ?);

-- READ SENSOR CONFIGURATION BY SENSOR ID
SELECT
    sensor_id,
    humidity_threshold,
    tamper_monitoring_enabled,
    reporting_interval_seconds,
    updated_at
FROM sensor_configuration
WHERE sensor_id = ?;

-- INSERT SENSOR READING
INSERT INTO sensor_reading (
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
)
VALUES (?, ?, ?, ?, ?, ?);

-- READ SENSOR READING BY ID
SELECT
    reading_id,
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
FROM sensor_reading
WHERE reading_id = ?;

-- READ READINGS BY SENSOR ID
SELECT
    reading_id,
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
FROM sensor_reading
WHERE sensor_id = ?
ORDER BY recorded_at DESC;

-- READ LATEST SENSOR READING
SELECT
    reading_id,
    sensor_id,
    latitude,
    longitude,
    humidity,
    tamper_detected,
    recorded_at
FROM sensor_reading
WHERE sensor_id = ?
ORDER BY recorded_at DESC
    LIMIT 1;

-- UPDATE SENSOR ACTIVITY
UPDATE sensor
SET last_active_at = CURRENT_TIMESTAMP,
    status = 'ACTIVE'
WHERE sensor_id = ?;

-- DETECT INACTIVE SENSORS
SELECT
    sensor_id,
    warehouse_id,
    status,
    last_active_at
FROM sensor
WHERE last_active_at < CURRENT_TIMESTAMP - INTERVAL '1 day'
ORDER BY last_active_at ASC;

-- READ SENSORS WITH CONFIGURATION
SELECT
    s.sensor_id,
    s.warehouse_id,
    s.status,
    s.last_active_at,
    s.created_at,
    sc.humidity_threshold,
    sc.tamper_monitoring_enabled,
    sc.reporting_interval_seconds,
    sc.updated_at
FROM sensor s
         LEFT JOIN sensor_configuration sc
                   ON s.sensor_id = sc.sensor_id
ORDER BY s.sensor_id;
