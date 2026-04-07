-- SEARCH ITEMS WITH OPTIONAL FILTERS, SORTING, PAGINATION
SELECT
    i.item_id,
    i.item_name,
    i.warehouse_id,
    i.created_at
FROM item i
WHERE
    (? IS NULL OR i.warehouse_id = ?)
  AND (? IS NULL OR LOWER(i.item_name) LIKE ?)
ORDER BY i.created_at DESC
    LIMIT ? OFFSET ?;

-- SHOW ITEM WITH WAREHOUSE AND ASSIGNED SENSORS
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

-- NUMBER OF ALERTS PER SENSOR
SELECT
    s.sensor_id,
    COUNT(a.alert_id) AS alert_count
FROM sensor s
         LEFT JOIN alert a
                   ON s.sensor_id = a.sensor_id
GROUP BY s.sensor_id
ORDER BY s.sensor_id;

-- NUMBER OF ITEMS PER WAREHOUSE
SELECT
    w.warehouse_id,
    w.location,
    COUNT(i.item_id) AS item_count
FROM warehouse w
         LEFT JOIN item i
                   ON w.warehouse_id = i.warehouse_id
GROUP BY w.warehouse_id, w.location
ORDER BY w.warehouse_id;

-- NUMBER OF SENSORS ASSIGNED TO EACH ITEM
SELECT
    i.item_id,
    i.item_name,
    COUNT(sia.sensor_id) AS sensor_count
FROM item i
         LEFT JOIN sensor_item_assignment sia
                   ON i.item_id = sia.item_id
GROUP BY i.item_id, i.item_name
ORDER BY i.item_id;

-- TOP 5 SENSORS BY NUMBER OF ALERTS
SELECT
    s.sensor_id,
    COUNT(a.alert_id) AS alert_count
FROM sensor s
         LEFT JOIN alert a
                   ON s.sensor_id = a.sensor_id
GROUP BY s.sensor_id
ORDER BY alert_count DESC, s.sensor_id ASC
    LIMIT 5;

-- TOP 5 ITEMS MONITORED BY MOST SENSORS
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

-- TOP 5 WAREHOUSES BY NUMBER OF ALERTS
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

-- LATEST READING FOR EACH SENSOR
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

-- READINGS EXCEEDING HUMIDITY THRESHOLD
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

-- TAMPER EVENTS
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
