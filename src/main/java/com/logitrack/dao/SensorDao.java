package com.logitrack.dao;

import com.logitrack.db.JdbcTemplate;
import com.logitrack.model.Sensor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SensorDao {

    private final JdbcTemplate jdbcTemplate;

    public SensorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Sensor sensor) {
        String sql = """
                INSERT INTO sensor (sensor_id, warehouse_id, status, last_active_at, created_at)
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.execute(
                sql,
                sensor.getSensorId(),
                sensor.getWarehouseId(),
                sensor.getStatus(),
                Timestamp.valueOf(sensor.getLastActiveAt()),
                Timestamp.valueOf(sensor.getCreatedAt())
        );
    }

    public Sensor findById(Long sensorId) {
        String sql = """
                SELECT sensor_id, warehouse_id, status, last_active_at, created_at
                FROM sensor
                WHERE sensor_id = ?
                """;

        return jdbcTemplate.findOne(sql, this::mapRow, sensorId);
    }

    public List<Sensor> findAll() {
        String sql = """
                SELECT sensor_id, warehouse_id, status, last_active_at, created_at
                FROM sensor
                ORDER BY sensor_id
                """;

        return jdbcTemplate.findMany(sql, this::mapRow);
    }

    private Sensor mapRow(ResultSet rs) {
        try {
            return new Sensor(
                    rs.getLong("sensor_id"),
                    rs.getLong("warehouse_id"),
                    rs.getString("status"),
                    rs.getTimestamp("last_active_at").toLocalDateTime(),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map sensor", e);
        }
    }
}