package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminServices {
    int addCourse(String title, String code, String dept, int credits) {
        String query = "INSERT INTO COURSES VALUES (?, ?, ?, ?)";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, code);
            ps.setString(3, dept);
            ps.setInt(4, credits);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    int removeCourse(String code) {
        String query = "DELETE FROM COURSES WHERE id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, code);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    int getGrade() {
        return 1;
    }

    int generateTranscript() {
        return 1;
    }
}
