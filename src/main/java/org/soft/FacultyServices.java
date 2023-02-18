package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FacultyServices {
    String facultyID;

    FacultyServices(String facultyID) {
        this.facultyID = facultyID;
    }

    int addOffering(String course_id, String semester, String[] for_dept, boolean[] is_core) {
        String query = "INSERT INTO offerings values (?, ?, ?, ?, ?)";
        try {
            Connection con = DatabaseService.getConnection();
            for (int i = 0; i < for_dept.length; i++) {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, course_id);
                ps.setString(2, "Offering");
                ps.setString(3, semester);
                ps.setString(4, for_dept[i]);
                ps.setBoolean(5, is_core[i]);
                ps.execute();
            }
            query = "INSERT INTO offering_instructors values (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course_id);
            ps.setString(2, semester);
            ps.setString(3, facultyID);
            ps.setBoolean(4, true);
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    int removeOffering(String course_id, String semester) {

        String query = "UPDATE offerings SET status = 'Cancelled' WHERE course_id = ? and semester = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course_id);
            ps.setString(2, semester);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    int uploadGrades() {
        return 1;
    }
}
