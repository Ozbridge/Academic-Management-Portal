package org.soft;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class StudentServices extends AcademicServices {
    String studentId;
    String dept;

    public StudentServices(String studentId, String dept) {
        this.studentId = studentId;
        this.dept = dept;
    }

    int getRegisteredCredits(String semester) {
        int ans = 0;
        String query = "SELECT SUM(credits) " +
                "FROM enrollments, courses " +
                "WHERE courses.id = enrollments.course_id " +
                "and student_id = ? and semester = ? and status != 'Dropped'";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentId);
            ps.setString(2, semester);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ans = rs.getInt(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int getEarnedCredits(String semester) {
        int ans = 0;
        String query = "SELECT SUM(credits*(select weight from grades where enrollments.grade = grades.grade)) " +
                "FROM enrollments, courses " +
                "WHERE courses.id = enrollments.course_id " +
                "and student_id = ? and semester = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentId);
            ps.setString(2, semester);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ans = rs.getInt(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    ArrayList<String> getCompletedCourses() {
        ArrayList<String> ans = new ArrayList<>();
        try {
            String query = "SELECT course_id from enrollments where student_id = ? and grade is not null and grade <> 'F'";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ans.add(rs.getString(1));
            }
            con.close();
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }

    boolean hasCompletedPrerequisites(String courseid) {
        ArrayList<String> completedCourses = getCompletedCourses();

        for (String course :
                getPrerequisites(courseid)) {
            if (!completedCourses.contains(course)) {
                return false;
            }
        }
        return true;
    }

    int creditRequest(String courseid, String semester, String creditedas) {
        Date deadline = getAddDropDate(semester);
        if (deadline == null
                || deadline.compareTo(new Date()) < 0
                || getCredits(courseid) + getRegisteredCredits(semester) > getCreditLimits(semester)
                || !hasCompletedPrerequisites(courseid)
        ) {
            return -1;
        }

        String query = "SELECT course_id, is_core FROM offerings WHERE course_id = ? and semester = ? and for_dept = ? and status = 'Offering'";
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
            //noinspection JpaQueryApiInspection
            ps.setString(4, semester);
            //noinspection JpaQueryApiInspection
            ps.setString(5, null);
            //noinspection JpaQueryApiInspection
            ps.setString(6, null);
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int dropRequest(String courseid, String semester) {
        String query = "SELECT course_id, status, grade FROM enrollments WHERE course_id = ? and student_id = ? and semester = ?";
        Date deadline = getAddDropDate(semester);

        if (deadline == null || deadline.compareTo(new Date()) < 0) {
            return -1;
        }
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
            rs.next();
            if (rs.getString(3) != null) {
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

    double calculateCGPA(String semester) {
        int creds = getRegisteredCredits(semester), earnedCreds = getEarnedCredits(semester);
//        System.out.println(creds);
//        System.out.println(earnedCreds);
        return earnedCreds / (creds * 1.0);
    }
}
