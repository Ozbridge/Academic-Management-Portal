package org.soft;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class AdminMenuTest {

    static AdminMenu adminMenu;

    @BeforeAll
    static void init() {
        adminMenu = new AdminMenu();
        DatabaseService.setAutoCommit(false);
    }

    @Test
    void showOptions() {
        adminMenu.showOptions();
    }

    @Test
    void doStuff() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        String input = "";
        ByteArrayInputStream in;

        input = "CS503\nMachine Learning\nCSE\n4\nfalse\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminMenu.doStuff("1");

        input = "CS304\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminMenu.doStuff("2");

        input = "2020csb1198\n2023-I\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminMenu.doStuff("3");

        input = "2020csb1198\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminMenu.doStuff("4");

        input = "2020csb1198\nCS304\n2022-II\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminMenu.doStuff("5");

        input = "2020csb1198\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminMenu.doStuff("6");

        adminMenu.doStuff("7");
        
        adminMenu.doStuff("8");

        System.setIn(sysInBackup);
    }

    @AfterAll
    static void end() {
        DatabaseService.setAutoCommit(true);
    }
}