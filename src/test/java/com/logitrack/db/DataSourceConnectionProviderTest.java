package com.logitrack.db;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceConnectionProviderTest {

    @Test
    void getConnection_shouldReturnConnection() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:ds_test;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");

        DataSourceConnectionProvider provider = new DataSourceConnectionProvider(ds);

        try (Connection connection = provider.getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        }
    }
}