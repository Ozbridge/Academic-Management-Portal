package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class StudentServices extends Services {
    String studentId;
    String dept;

    public StudentServices(String studentId, String dept) {
        this.studentId = studentId;
        this.dept = dept;
    }

    int creditRequest(String courseid, String semester, String creditedas) {
        Date deadline = getAddDropDate(semester);
        if (deadline == null) {
            return -1;
        }
        if (deadline <= Date.)
            String query = "SELECT course_id, is_core FROM offerings WHERE course_id = ? and semester = ? and for_dept = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ps.setString(2, semester);
            ps.setString(3, dept);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                con.close();
                return -1;
            }

            query = "INSERT INTO enrollments values (?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ps.setString(2, studentId);
            ps.setString(3, "Enrolled");
            ps.setString(4, semester);
            ps.setString(5, null);
            ps.setString(6, null);
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int dropRequest(String courseid, String semester) {
        String query = "SELECT course_id, status FROM enrollments WHERE course_id = ? and student_id = ? and semester = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ps.setString(2, studentId);
            ps.setString(3, semester);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                con.close();
                return -1;
            }

            query = "UPDATE enrollments SET status = 'Dropped' WHERE course_id = ? and student_id = ? and semester = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ps.setString(2, studentId);
            ps.setString(3, semester);
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int withdrawRequest(int courseid, int semester) {
        return 0;
    }

    int calculateCGPA() {
        return 0;

    }
}
