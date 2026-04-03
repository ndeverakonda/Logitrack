package com.logitrack.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncorrectResultSizeExceptionTest {

    @Test
    void constructor_shouldStoreMessage() {
        IncorrectResultSizeException exception =
                new IncorrectResultSizeException("too many rows");

        assertEquals("too many rows", exception.getMessage());
    }
}