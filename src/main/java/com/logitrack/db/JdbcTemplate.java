package com.logitrack.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class JdbcTemplate {

    private final ConnectionProvider connectionProvider;

    public JdbcTemplate(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void execute(String query, Object... args) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            bindArguments(preparedStatement, args);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to execute query: " + query, e);
        }
    }

    public void execute(String query, Consumer<PreparedStatement> statementConsumer) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            statementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to execute query: " + query, e);
        }
    }

    public <T> T findOne(String query, Function<ResultSet, T> mapper, Object... args) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            bindArguments(preparedStatement, args);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                T result = mapper.apply(resultSet);

                if (resultSet.next()) {
                    throw new IncorrectResultSizeException(
                            "Expected exactly one result but found more than one for query: " + query
                    );
                }

                return result;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to execute findOne query: " + query, e);
        }
    }

    public <T> List<T> findMany(String query, Function<ResultSet, T> mapper, Object... args) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            bindArguments(preparedStatement, args);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(mapper.apply(resultSet));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to execute findMany query: " + query, e);
        }
    }

    private void bindArguments(PreparedStatement preparedStatement, Object... args) {
        try {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to bind query arguments", e);
        }
    }
}