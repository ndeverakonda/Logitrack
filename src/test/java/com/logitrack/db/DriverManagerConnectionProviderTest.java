package com.logitrack.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DriverManagerConnectionProviderTest {

    @Test
    void getConnection_shouldReturnConnection_forValidH2Url() throws Exception {
        DriverManagerConnectionProvider provider =
                new DriverManagerConnectionProvider(
                        "jdbc:h2:mem:driver_test;DB_CLOSE_DELAY=-1",
                        "sa",
                        ""
                );

        try (Connection connection = provider.getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        }
    }

    @Test
    void getConnection_shouldThrowDatabaseException_forInvalidUrl() {
        DriverManagerConnectionProvider provider =
                new DriverManagerConnectionProvider(
                        "jdbc:invalid:mem:test",
                        "sa",
                        ""
                );

        assertThrows(DatabaseException.class, provider::getConnection);
    }
}