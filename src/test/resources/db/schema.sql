CREATE TABLE sensor (
                        sensor_id BIGINT PRIMARY KEY,
                        warehouse_id BIGINT,
                        status VARCHAR(50),
                        last_active_at TIMESTAMP,
                        created_at TIMESTAMP
);

CREATE TABLE sensor_reading (
                                reading_id BIGSERIAL PRIMARY KEY,
                                sensor_id BIGINT,
                                latitude DOUBLE,
                                longitude DOUBLE,
                                humidity DOUBLE,
                                tamper_detected BOOLEAN,
                                recorded_at TIMESTAMP
);

CREATE TABLE alert (
                       alert_id BIGSERIAL PRIMARY KEY,
                       sensor_id BIGINT,
                       reading_id BIGINT,
                       alert_type VARCHAR(100),
                       message TEXT,
                       severity VARCHAR(50),
                       created_at TIMESTAMP
);