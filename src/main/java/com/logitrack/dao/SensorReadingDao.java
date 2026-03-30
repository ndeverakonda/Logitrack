package com.logitrack.dao;

import com.logitrack.db.JdbcTemplate;
import com.logitrack.model.SensorReading;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SensorReadingDao {

    private final JdbcTemplate jdbcTemplate;

    public SensorReadingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(SensorReading reading) {
        String sql = """
                INSERT INTO sensor_reading
                (sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.execute(
                sql,
                reading.getSensorId(),
                reading.getLatitude(),
                reading.getLongitude(),
                reading.getHumidity(),
                reading.isTamperDetected(),
                Timestamp.valueOf(reading.getRecordedAt())
        );
    }

    public SensorReading findById(Long readingId) {
        String sql = """
                SELECT reading_id, sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at
                FROM sensor_reading
                WHERE reading_id = ?
                """;

        return jdbcTemplate.findOne(sql, this::mapRow, readingId);
    }

    public List<SensorReading> findBySensorId(Long sensorId) {
        String sql = """
                SELECT reading_id, sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at
                FROM sensor_reading
                WHERE sensor_id = ?
                ORDER BY recorded_at DESC
                """;

        return jdbcTemplate.findMany(sql, this::mapRow, sensorId);
    }

    public List<SensorReading> findAll() {
        String sql = """
                SELECT reading_id, sensor_id, latitude, longitude, humidity, tamper_detected, recorded_at
                FROM sensor_reading
                ORDER BY recorded_at DESC
                """;

        return jdbcTemplate.findMany(sql, this::mapRow);
    }

    private SensorReading mapRow(ResultSet rs) {
        try {
            return new SensorReading(
                    rs.getLong("reading_id"),
                    rs.getLong("sensor_id"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getDouble("humidity"),
                    rs.getBoolean("tamper_detected"),
                    rs.getTimestamp("recorded_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map sensor reading", e);
        }
    }
}