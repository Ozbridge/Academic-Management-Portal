package org.soft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

class StudentServicesTest {

    static StudentServices studentServices;

    @BeforeAll
    static void init() {
        DatabaseService.setAutoCommit(false);
        studentServices = new StudentServices("2020csb1198", "CSE");
    }

    @Test
    void getRegisteredCourses() throws SQLException {
        assertEquals(0, studentServices.getRegisteredCourses("2022-II"));
    }

    @Test
    void getRegisteredCredits() throws SQLException {
        assertEquals(6, studentServices.getRegisteredCredits("2023-I"));
        assertEquals(0, studentServices.getRegisteredCredits("2023-II"));
    }

    @Test
    void getEarnedCredits() throws SQLException {
        assertEquals(0, studentServices.getEarnedCredits("2022-II"));
    }

    @Test
    void getCompletedCourses() throws SQLException {
        ArrayList<String> list = new ArrayList<>(), expected = studentServices.getCompletedCourses();
        list.add("CP301");
        list.add("HS303");
        list.add("CS522");
        assertTrue(list.containsAll(expected) && list.size() == expected.size());
    }


    @Test
    void creditRequest() {
        assertEquals(0, studentServices.creditRequest("GE111", "2023-I"));
        assertEquals(1, studentServices.creditRequest("CP303", "2023-I"));
        assertEquals(0, studentServices.creditRequest("CP301", "2023-I"));
        studentServices = new StudentServices("2020csb1153", "CSE");
        assertEquals(1, studentServices.creditRequest("CS503", "2022-II"));
        assertEquals(1, studentServices.creditRequest("CS533", "2023-I"));
        assertEquals(0, studentServices.creditRequest("GE111", "2023-I"));
        assertEquals(0, studentServices.creditRequest("CP301", "2023-I"));
        assertEquals(1, studentServices.creditRequest("HS303", "2023-I"));
        assertEquals(1, studentServices.creditRequest("CP303", "2023-I"));
        studentServices = new StudentServices("2020csb1198", "CSE");

    }

    @Test
    void dropRequest() {
        assertEquals(1, studentServices.dropRequest("CS599", "2023-I"));
        assertEquals(1, studentServices.dropRequest("CS503", "2024-I"));
        assertEquals(1, studentServices.dropRequest("CS503", "2022-II"));
        assertEquals(0, studentServices.dropRequest("CP302", "2023-I"));
    }


    @Test
    void calculateCGPA() throws SQLException {
        assertEquals(9.7, studentServices.calculateCGPA());
    }

    @AfterAll
    static void end() {
        DatabaseService.setAutoCommit(true);
    }
}