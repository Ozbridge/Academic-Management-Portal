package org.soft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServicesTest {

    static FacultyServices facultyServices;

    @BeforeAll
    static void init() {
        DatabaseService.setAutoCommit(false);
        facultyServices = new FacultyServices("cse001", "CSE");
    }
    

    @Test
    void addOffering() {
        String forDept[] = {"CSE", "CHE"};
        boolean isCore[] = {true, false};
        assertEquals(0, facultyServices.addOffering("CS503", "2023-I", forDept, isCore, 7.5));
        assertEquals(1, facultyServices.addOffering("CS599", "2023-I", forDept, isCore, 7.5));
    }

    @Test
    void removeOffering() {
        assertEquals(0, facultyServices.removeOffering("CS503", "2022-II"));
    }

    @Test
    void uploadGrades() {
        File fd = new File("/Users/rishabhjain/IdeaProjects/softE/SoftE/datatemp/gradeCS503.csv");
        assertEquals(0, facultyServices.uploadGrades("CS503", "2022-II", fd));
        fd = new File("/Users/rishabhjain/IdeaProjects/softE/SoftE/datatemp/gradeCS99.csv");
        assertEquals(1, facultyServices.uploadGrades("CS304", "2022-II", fd));
        fd = new File("/Users/rishabhjain/IdeaProjects/softE/SoftE/datatemp/gradeCS99.csv");
        assertEquals(1, facultyServices.uploadGrades("CS503", "2022-II", fd));

    }

    @Test
    void getGrade() {
        assertEquals("A", facultyServices.getGrade("2020csb1198", "CS503", "2022-II"));
        assertEquals(null, facultyServices.getGrade("2020csb1198", "CS539", "2022-II"));
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