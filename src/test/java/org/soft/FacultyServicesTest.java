package org.soft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FacultyServicesTest {

    static FacultyServices facultyServices;

    @BeforeAll
    static void init() {
        DatabaseService.setAutoCommit(false);
        facultyServices = new FacultyServices("cse001", "CSE");
    }

    @Test
    void seeSelfOfferings() {
        assertEquals(0, facultyServices.seeSelfOfferings("2022-II"));
    }

    @Test
    void addOffering() {
        String[] forDept = {"CSE", "CHE"};
        boolean[] isCore = {true, false};
        assertEquals(0, facultyServices.addOffering("CS503", "2023-I", forDept, isCore, 7.5));
        assertEquals(1, facultyServices.addOffering("CS599", "2023-I", forDept, isCore, 7.5));
        assertEquals(1, facultyServices.addOffering("CS401", "2023-I", forDept, isCore, 7.5));

    }

    @Test
    void removeOffering() {
        assertEquals(0, facultyServices.removeOffering("CS503", "2022-II"));
        assertEquals(1, facultyServices.removeOffering("CS304", "2022-II"));
    }

    @Test
    void uploadGrades() {
        String path = System.getProperty("user.dir") + "/datatemp/gradeCS503.csv";
        System.out.println(path);
        File fd = new File(path);
        assertEquals(0, facultyServices.uploadGrades("CS503", "2022-II", fd));
        assertEquals(1, facultyServices.uploadGrades("CS304", "2022-II", fd));
        FacultyServices temp = facultyServices;
        facultyServices = new FacultyServices("hs002", "HS");
        assertEquals(1, facultyServices.uploadGrades("CS503", "2022-II", fd));
        facultyServices = temp;

    }

    @Test
    void getGrade() {
        assertNull(facultyServices.getGrade("2020csb1198", "CS503", "2022-II"));
        assertNull(facultyServices.getGrade("2020csb1198", "CS539", "2022-II"));
    }

    @Test
    void getContactDetails() {
        assertEquals(0, facultyServices.getContactDetails("2020csb1198"));
        assertEquals(1, facultyServices.getContactDetails("2020csb9999"));
    }

    @AfterAll
    static void end() {
        DatabaseService.setAutoCommit(true);
    }
}