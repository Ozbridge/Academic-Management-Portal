package org.soft;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    static AuthService authService;

    @BeforeAll
    static void init() {
        authService = new AuthService();
    }

    @Test
    void login() {
        String[] expected = {"2020csb1198", "STU", "CSE"};
        assertArrayEquals(expected, authService.login("2020csb1198", "rishabh"));
        assertArrayEquals(new String[]{}, authService.login("2020csb1999", "passowrd"));
    }
}