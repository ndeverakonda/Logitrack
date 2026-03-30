package com.logitrack.dao;

import com.logitrack.db.JdbcTemplate;
import com.logitrack.model.Warehouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class WarehouseDao {

    private final JdbcTemplate jdbcTemplate;
    private final ItemDao itemDao;

    public WarehouseDao(JdbcTemplate jdbcTemplate, ItemDao itemDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemDao = itemDao;
    }

    public void save(Warehouse warehouse) {
        String sql = """
                INSERT INTO warehouse (warehouse_id, location, capacity, created_at)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.execute(
                sql,
                warehouse.getWarehouseId(),
                warehouse.getLocation(),
                warehouse.getCapacity(),
                Timestamp.valueOf(warehouse.getCreatedAt())
        );
    }

    public Warehouse findById(Long warehouseId) {
        String sql = """
                SELECT warehouse_id, location, capacity, created_at
                FROM warehouse
                WHERE warehouse_id = ?
                """;

        return jdbcTemplate.findOne(sql, this::mapRow, warehouseId);
    }

    public List<Warehouse> findAll() {
        String sql = """
                SELECT warehouse_id, location, capacity, created_at
                FROM warehouse
                ORDER BY warehouse_id
                """;

        return jdbcTemplate.findMany(sql, this::mapRow);
    }

    public Warehouse findByIdWithItems(Long warehouseId) {
        Warehouse warehouse = findById(warehouseId);

        if (warehouse == null) {
            return null;
        }

        warehouse.setItems(itemDao.findByWarehouseId(warehouseId));
        return warehouse;
    }

    private Warehouse mapRow(ResultSet rs) {
        try {
            return new Warehouse(
                    rs.getLong("warehouse_id"),
                    rs.getString("location"),
                    rs.getInt("capacity"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map warehouse", e);
        }
    }
}