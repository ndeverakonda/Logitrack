package com.logitrack.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseExceptionTest {

    @Test
    void constructor_withMessage_shouldStoreMessage() {
        DatabaseException exception = new DatabaseException("db error");

        assertEquals("db error", exception.getMessage());
    }

    @Test
    void constructor_withMessageAndCause_shouldStoreMessageAndCause() {
        RuntimeException cause = new RuntimeException("root cause");
        DatabaseException exception = new DatabaseException("db error", cause);

        assertEquals("db error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}