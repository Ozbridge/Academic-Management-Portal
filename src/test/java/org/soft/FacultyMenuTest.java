package org.soft;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class FacultyMenuTest {

    static FacultyMenu facultyMenu;

    @BeforeAll
    static void init() {
        facultyMenu = new FacultyMenu("cse001", "CSE");
        DatabaseService.setAutoCommit(false);
    }

    @Test
    void showOptions() {
        facultyMenu.showOptions();
    }

    @Test
    void doStuff() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        String input = "";
        ByteArrayInputStream in;

        input = "CS503\n2023-I\n2\nCSE true\nCHE false\n7.0\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        facultyMenu.doStuff("1");

        input = "CS503\n2023-I\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        facultyMenu.doStuff("2");

        input = "CS503\n2023-I\n/Users/rishabhjain/IdeaProjects/softE/SoftE/datatemp/gradeCS503.csv\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        facultyMenu.doStuff("3");

        input = "CS503\n2022-II\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        facultyMenu.doStuff("4");

        input = "2020csb1198\nCS304\n2022-II\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        facultyMenu.doStuff("5");

        facultyMenu.doStuff("6");

        input = "2020csb1198\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        facultyMenu.doStuff("7");

        facultyMenu.doStuff("8");

    }

    @AfterAll
    static void end() {
        DatabaseService.setAutoCommit(true);
    }
}