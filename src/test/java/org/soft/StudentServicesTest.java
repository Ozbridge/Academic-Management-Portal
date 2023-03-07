package org.soft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentServicesTest {

    static StudentServices studentServices;

    @BeforeAll
    static void init() {
        DatabaseService.setAutoCommit(false);
        studentServices = new StudentServices("2020csb1198", "CSE");
    }

    @Test
    void getRegisteredCredits() {
        assertEquals(6, studentServices.getRegisteredCredits("2023-I"));
        assertEquals(0, studentServices.getRegisteredCredits("2023-II"));
    }

    @Test
    void getEarnedCredits() {
        assertEquals(8, studentServices.getEarnedCredits("2022-II"));
    }

    @Test
    void getCompletedCourses() {
        ArrayList<String> list = new ArrayList<>(), expected = studentServices.getCompletedCourses();
        list.add("CS503");
        list.add("CS304");
        assertTrue(list.containsAll(expected) && list.size() == expected.size());
    }


    @Test
    void creditRequest() {
        assertEquals(0, studentServices.creditRequest("GE111", "2023-I"));
        assertEquals(1, studentServices.creditRequest("CP303", "2023-I"));
        assertEquals(1, studentServices.creditRequest("CS533", "2023-I"));
    }

    @Test
    void dropRequest() {
        assertEquals(1, studentServices.dropRequest("CS599", "2023-I"));
        assertEquals(1, studentServices.dropRequest("CS503", "2022-II"));
        assertEquals(0, studentServices.dropRequest("CP302", "2023-I"));
    }


    @Test
    void calculateCGPA() {
        assertEquals(10.0d, studentServices.calculateCGPA());
    }

    @AfterAll
    static void end() {
        DatabaseService.setAutoCommit(true);
    }
}