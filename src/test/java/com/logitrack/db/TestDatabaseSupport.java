package com.logitrack.db;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TestDatabaseSupport {

    private TestDatabaseSupport() {
    }

    public static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:logitrack-" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    public static void runScript(DataSource dataSource, String classpathResource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = readClasspathFile(classpathResource);
            for (String part : sql.split(";")) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to run SQL script: " + classpathResource, e);
        }
    }

    private static String readClasspathFile(String classpathResource) {
        InputStream inputStream = TestDatabaseSupport.class.getClassLoader()
                .getResourceAsStream(classpathResource);

        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + classpathResource);
        }

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
