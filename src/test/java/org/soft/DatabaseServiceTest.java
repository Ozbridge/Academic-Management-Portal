package org.soft;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DatabaseServiceTest {

    @Test
    void setAutoCommit() {
        assertEquals(0, DatabaseService.setAutoCommit(false));
    }

    @Test
    void getConnection() {
        DatabaseService.setAutoCommit(true);
        assertNotEquals(null, DatabaseService.getConnection());
        DatabaseService.setAutoCommit(false);
        assertNotEquals(null, DatabaseService.getConnection());
    }
}