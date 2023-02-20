package org.soft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class AdminServices extends AcademicServices {
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

    boolean canGraduate(String studentid) {
        String query = "SELECT course_id, credited_as FROM enrollments, grades where student_id = ? and enrollments.grade = grades.grade and grades.weight != 0";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentid);
            ResultSet rs = ps.executeQuery();
            HashMap<String, Integer> creds = new HashMap<String, Integer>();
            while (rs.next()) {
                int prev = creds.get(rs.getString(2)) == null ? 0 : creds.get(rs.getString(2));
                int courseCredits = getCredits(rs.getString(1));
                creds.put(rs.getString(2), prev + courseCredits);
            }
            query = "SELECT credit_type, num_required FROM graduating_requirements";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int earnedInCategory = 0;
                if (creds.get(rs.getString(1)) != null)
                    earnedInCategory = creds.get(rs.getString(1));
//                System.out.println(rs.getString(1) + " " + earnedInCategory);
                if (earnedInCategory < rs.getInt(2))
                    return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    int removeCourse(String code) {
        String query = "DELETE FROM COURSES WHERE id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, code);
            ps.execute();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    String getGrade(String studentid, String courseid, String semester) {
        String query = "SELECT grade FROM enrollments WHERE student_id = ? and semester = ? and course_id = ? and status != 'Dropped'";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentid);
            ps.setString(2, semester);
            ps.setString(3, courseid);
            ResultSet rs = ps.executeQuery();
            String ans = null;
            if (rs.next()) {
                ans = rs.getString(1);
            }
            con.close();
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "##";
    }

    String generateTranscript(String studentid, String semester) {
        String query = "SELECT course_id, grade FROM enrollments WHERE student_id = ? and semester = ? and status != 'Dropped'";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentid);
            ps.setString(2, semester);
            ResultSet rs = ps.executeQuery();
            StringBuilder ans = new StringBuilder();
            ans.append(studentid).append('\n').append(semester).append('\n');
            while (rs.next()) {
                ans.append(rs.getString(1)).append(":\t").append(rs.getString(2)).append("\n");
            }
            con.close();
            return ans.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "##";
    }
}
