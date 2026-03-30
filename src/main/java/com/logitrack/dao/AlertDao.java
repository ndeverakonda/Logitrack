package com.logitrack.dao;

import com.logitrack.db.JdbcTemplate;
import com.logitrack.model.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AlertDao {

    private final JdbcTemplate jdbcTemplate;

    public AlertDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Alert alert) {
        String sql = """
                INSERT INTO alert
                (sensor_id, reading_id, alert_type, message, severity, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.execute(
                sql,
                alert.getSensorId(),
                alert.getReadingId(),
                alert.getAlertType(),
                alert.getMessage(),
                alert.getSeverity(),
                Timestamp.valueOf(alert.getCreatedAt())
        );
    }

    public Alert findById(Long alertId) {
        String sql = """
                SELECT alert_id, sensor_id, reading_id, alert_type, message, severity, created_at
                FROM alert
                WHERE alert_id = ?
                """;

        return jdbcTemplate.findOne(sql, this::mapRow, alertId);
    }

    public List<Alert> findAll() {
        String sql = """
                SELECT alert_id, sensor_id, reading_id, alert_type, message, severity, created_at
                FROM alert
                ORDER BY created_at DESC
                """;

        return jdbcTemplate.findMany(sql, this::mapRow);
    }

    private Alert mapRow(ResultSet rs) {
        try {
            return new Alert(
                    rs.getLong("alert_id"),
                    rs.getLong("sensor_id"),
                    rs.getObject("reading_id", Long.class),
                    rs.getString("alert_type"),
                    rs.getString("message"),
                    rs.getString("severity"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map alert", e);
        }
    }
}