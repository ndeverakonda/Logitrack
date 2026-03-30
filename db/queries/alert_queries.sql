-- INSERT ALERT
INSERT INTO alert (
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
)
VALUES (?, ?, ?, ?, ?, ?);

-- READ ALERT BY ID
SELECT
    alert_id,
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
FROM alert
WHERE alert_id = ?;

-- READ ALL ALERTS
SELECT
    alert_id,
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
FROM alert
ORDER BY created_at DESC;

-- READ ALERTS FOR ONE SENSOR
SELECT
    alert_id,
    sensor_id,
    reading_id,
    alert_type,
    message,
    severity,
    created_at
FROM alert
WHERE sensor_id = ?
ORDER BY created_at DESC;

-- READ RECENT ALERTS
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

-- JOINED ALERT VIEW WITH WAREHOUSE AND ITEM CONTEXT
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