package org.soft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AcademicServices {
    int getEnrollmentList(String courseid, String semeseter) {
        try {
            String query = "select username, userid, status from enrollments, users where userid = student_id  and course_id = ? and semester = ?";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ps.setString(2, semeseter);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Student Name: " + rs.getString(1) +
                        " StudentID: " + rs.getString(2) +
                        " Status: " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    Date getAddDropDate(String semester) {
        String query = "SELECT add_drop from semesters where id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            rs.next();
            Date ans = rs.getDate(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    int getCredits(String courseid) {
        String query = "SELECT credits from courses where id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return 0;
            }
            rs.next();
            int ans = rs.getInt(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    ArrayList<String> getPrerequisites(String courseid) {
        try {
            Connection con = DatabaseService.getConnection();
            String query = "SELECT precourseid FROM prerequisites WHERE courseid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> ans = new ArrayList<>();
            while (rs.next()) {
                ans.add(rs.getString(1));
            }
            con.close();
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    int seeOfferings(String semester) {
        try {
            String query = "select course_id, name, for_dept, is_core from offerings, courses where course_id = id and semester = ?";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Course ID: " + rs.getString(1) +
                        " Title: " + rs.getString(2) +
                        " Dept: " + rs.getString(3) + " Type: " + ((rs.getBoolean(4)) ? "Core" : "Elective"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    void updateContactDetails(String userid) {
        try {
            String query;
            Scanner sc = new Scanner(System.in);
            Connection con = DatabaseService.getConnection();
            String ip;
            System.out.println("Enter 'y' to update phone or 'n' to skip: ");
            ip = sc.next();
            if (ip.equals("y")) {
                System.out.println("Enter new phone number (without spaces):");
                ip = sc.next();
                query = "update users set phone = ? where userid = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, ip);
                ps.setString(2, userid);
                ps.execute();
            }
            System.out.println("Enter 'y' to update email or 'n' to skip: ");
            ip = sc.next();
            if (ip.equals("y")) {
                System.out.println("Enter new phone email (without spaces):");
                ip = sc.next();
                query = "update users set email = ? where userid = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, ip);
                ps.setString(2, userid);
                ps.execute();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
