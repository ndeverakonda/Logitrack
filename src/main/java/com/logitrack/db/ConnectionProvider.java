package com.logitrack.db;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection();
}