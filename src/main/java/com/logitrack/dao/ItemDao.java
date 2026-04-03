package com.logitrack.dao;

import com.logitrack.db.JdbcTemplate;
import com.logitrack.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Item item) {
        String sql = """
                INSERT INTO item (item_id, item_name, warehouse_id, created_at)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.execute(
                sql,
                item.getItemId(),
                item.getItemName(),
                item.getWarehouseId(),
                Timestamp.valueOf(item.getCreatedAt())
        );
    }

    public Item findById(Long itemId) {
        String sql = """
                SELECT item_id, item_name, warehouse_id, created_at
                FROM item
                WHERE item_id = ?
                """;

        return jdbcTemplate.findOne(sql, this::mapRow, itemId);
    }

    public List<Item> findAll() {
        String sql = """
                SELECT item_id, item_name, warehouse_id, created_at
                FROM item
                ORDER BY item_id
                """;

        return jdbcTemplate.findMany(sql, this::mapRow);
    }

    private Item mapRow(ResultSet rs) {
        try {
            return new Item(
                    rs.getLong("item_id"),
                    rs.getString("item_name"),
                    rs.getLong("warehouse_id"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map item", e);
        }
    }
}