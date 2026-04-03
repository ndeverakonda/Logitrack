CREATE TABLE IF NOT EXISTS warehouse(
                                        warehouse_id BIGINT PRIMARY KEY,
                                        location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS item (
                                    item_id BIGINT PRIMARY KEY,
                                    item_name VARCHAR(255) NOT NULL,
    warehouse_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_item_warehouse
    FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS sensor (
                                      sensor_id BIGINT PRIMARY KEY,
                                      warehouse_id BIGINT NOT NULL,
                                      status VARCHAR(50) NOT NULL,
    last_active_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sensor_warehouse
    FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS sensor_configuration (
                                                    sensor_id BIGINT PRIMARY KEY,
                                                    humidity_threshold DOUBLE PRECISION,
                                                    tamper_monitoring_enabled BOOLEAN,
                                                    reporting_interval_seconds INT,
                                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                                    CONSTRAINT fk_config_sensor
                                                    FOREIGN KEY (sensor_id)
    REFERENCES sensor(sensor_id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS sensor_reading (
                                              reading_id BIGSERIAL PRIMARY KEY,
                                              sensor_id BIGINT NOT NULL,
                                              latitude DOUBLE PRECISION,
                                              longitude DOUBLE PRECISION,
                                              humidity DOUBLE PRECISION,
                                              tamper_detected BOOLEAN,
                                              recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                              CONSTRAINT fk_reading_sensor
                                              FOREIGN KEY (sensor_id)
    REFERENCES sensor(sensor_id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS alert (
                                     alert_id BIGSERIAL PRIMARY KEY,
                                     sensor_id BIGINT NOT NULL,
                                     reading_id BIGINT,
                                     alert_type VARCHAR(100),
    message TEXT,
    severity VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_alert_sensor
    FOREIGN KEY (sensor_id)
    REFERENCES sensor(sensor_id)
    ON DELETE CASCADE,

    CONSTRAINT fk_alert_reading
    FOREIGN KEY (reading_id)
    REFERENCES sensor_reading(reading_id)
    ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS sensor_item_assignment (
                                                      sensor_id BIGINT NOT NULL,
                                                      item_id BIGINT NOT NULL,
                                                      assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                                      PRIMARY KEY (sensor_id, item_id),

    CONSTRAINT fk_assignment_sensor
    FOREIGN KEY (sensor_id)
    REFERENCES sensor(sensor_id)
    ON DELETE CASCADE,

    CONSTRAINT fk_assignment_item
    FOREIGN KEY (item_id)
    REFERENCES item(item_id)
    ON DELETE CASCADE
    );