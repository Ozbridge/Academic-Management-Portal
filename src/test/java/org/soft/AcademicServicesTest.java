package org.soft;

import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AcademicServicesTest {

    static AcademicServices academicServices;

    @BeforeAll
    static void init() {
        academicServices = new AcademicServices();
    }

    @org.junit.jupiter.api.Test
    void getEnrollmentList() {
        int ret = academicServices.getEnrollmentList("CS503", "2022-II");
        assertEquals(0, ret);
    }

    @org.junit.jupiter.api.Test
    void getAddDropDate() {
        Date date = academicServices.getAddDropDate("2022-II");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("2023-01-16", dateFormat.format(date));
        date = academicServices.getAddDropDate("2025-I");
        assertNull(date);
    }

    @org.junit.jupiter.api.Test
    void getCredits() {
        assertEquals(4, academicServices.getCredits("CS503"));
        assertEquals(0, academicServices.getCredits("CS666"));
    }

    @org.junit.jupiter.api.Test
    void getPrerequisites() {
        ArrayList<String> list = academicServices.getPrerequisites("CP303");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("CP302");
        assertTrue(list.containsAll(expected) && list.size() == expected.size());
    }

    @org.junit.jupiter.api.Test
    void seeOfferings() {
        assertEquals(0, academicServices.seeOfferings("2022-II"));
    }

    @org.junit.jupiter.api.Test
    void updateContactDetails() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("y\n+6392396338\ny\n2020csb1198@iitrpr.ac.in\n".getBytes());
        System.setIn(in);
        DatabaseService.setAutoCommit(false);
        academicServices.updateContactDetails("2020csb1198");
        DatabaseService.setAutoCommit(true);
        System.setIn(sysInBackup);

    }
}