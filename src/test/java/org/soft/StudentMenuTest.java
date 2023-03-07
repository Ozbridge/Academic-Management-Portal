package org.soft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class StudentMenuTest {

    static StudentMenu studentMenu;

    @BeforeAll
    static void init() {
        studentMenu = new StudentMenu("2020csb1198", "CSE");
        DatabaseService.setAutoCommit(false);
    }

    @Test
    void showOptions() {
        studentMenu.showOptions();
    }

    @Test
    void doStuff() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        String input = "";
        ByteArrayInputStream in;

        input = "2023-II\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        studentMenu.doStuff("1");

        studentMenu.doStuff("2");

        input = "CS503\n2023-I\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        studentMenu.doStuff("3");

        input = "CS503\n2022-II\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        studentMenu.doStuff("4");

        studentMenu.doStuff("5");
        input = "2023-I\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        studentMenu.doStuff("6");
//        studentMenu.doStuff("7");
        studentMenu.doStuff("8");


        System.setIn(sysInBackup);
    }

    @AfterAll
    static void end() {
        DatabaseService.setAutoCommit(true);
    }
}