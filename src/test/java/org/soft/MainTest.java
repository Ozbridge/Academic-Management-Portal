package org.soft;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        String input = "abcd\n1234\n2020csb1198\nrishabh\nabc\nlogout\n" +
                "abc\ncse001\nfaculty\nabc\nlogout\n" +
                "abd\nad001\ndean\nabc\nlogout\n" +
                "quit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Main.main(new String[]{});
        System.setIn(sysInBackup);

    }
}