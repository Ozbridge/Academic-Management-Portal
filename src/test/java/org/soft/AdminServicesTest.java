package org.soft;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class AdminServicesTest {

    static AdminServices adminServices;

    @BeforeAll
    static void setUp() {
        adminServices = new AdminServices();
        DatabaseService.setAutoCommit(false);
    }

    @Test
    void addCourse() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("0\n0\n".getBytes());
        System.setIn(in);
        assertEquals(0, adminServices.addCourse("Design of Algorithms", "CS302", "CSE", 3, false));
        assertEquals(1, adminServices.addCourse("Financial Derivative Pricing", "MA628", "MA", 4, false));
        System.setIn(sysInBackup);
    }

    @Test
    void addPrerequisites() {
        assertEquals(0, adminServices.addPrerequisites("CS503", "CS304"));
        assertEquals(1, adminServices.addPrerequisites("CS504", "CS503"));
    }

    @Test
    void canGraduate() {
        assertEquals(true, adminServices.canGraduate("2020csb1198"));
        assertEquals(false, adminServices.canGraduate("2020csb9999"));
    }

    @Test
    void removeCourse() {
        assertEquals(0, adminServices.removeCourse("CS503"));
        assertEquals(0, adminServices.removeCourse("CS999"));
    }

    @Test
    void getGrade() {
        assertEquals("A", adminServices.getGrade("2020csb1198", "CS503", "2022-II"));
        assertEquals(null, adminServices.getGrade("2020csb1198", "CS539", "2022-II"));

    }

    @Test
    void generateTranscript() {
        assertNotEquals("", adminServices.generateTranscript("2020csb1198", "2022-II"));
    }

    @Test
    void getContactDetails() {
        assertEquals(0, adminServices.getContactDetails("2020csb1198"));
        assertEquals(1, adminServices.getContactDetails("2020csb9999"));
    }

    @AfterAll
    static void main() {
        DatabaseService.setAutoCommit(true);
    }
}