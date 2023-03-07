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
                "and student_id = ? and semester = ? and status = 'Enrolled'";
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
        String query = "SELECT SUM(credits) " +
                "FROM enrollments, courses " +
                "WHERE courses.id = enrollments.course_id  AND EXISTS (select * from grades where enrollments.grade = grades.grade and weight != 0)" +
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

    private int getWeightedEarnedCredits(String semester) {
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

    private boolean hasCompletedPrerequisites(String courseid) {
        ArrayList<String> completedCourses = getCompletedCourses();

        for (String course :
                getPrerequisites(courseid)) {
            if (!completedCourses.contains(course)) {
                return false;
            }
        }
        return true;
    }

    private int getCreditLimits(String semester) {
        String query = "select id from semesters where id < ? order by id desc limit 2";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();
            int ans = 0;
            for (int i = 0; i < 2; i++) {
                if (rs.next()) {
                    ans += getEarnedCredits(rs.getString(1));
                } else {
                    ans += 6;
                }
            }
            ans = (int) Math.ceil(ans * 1.25 / 2);
//            System.out.println(ans);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private double getCGPARequirement(String course, String semester) {
        String query = "Select cgpa from offerings where course_id = ? and semester = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course);
            ps.setString(2, semester);
            ResultSet rs = ps.executeQuery();
            double ans = 0;
            if (rs.next()) {
                ans = rs.getDouble(1);
            }
            con.close();
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int creditRequest(String courseid, String semester) {
        Date deadline = getAddDropDate(semester);
        if (deadline == null
                || deadline.compareTo(new Date()) < 0
                || getCredits(courseid) + getRegisteredCredits(semester) > getCreditLimits(semester)
                || !hasCompletedPrerequisites(courseid)
                || calculateCGPA() < getCGPARequirement(courseid, semester)
        ) {
            System.out.println("Unable to credit...");
            return 1;
        }

        String query = "SELECT course_id, is_core, isBTP FROM offerings, courses WHERE courses.id = course_id and course_id = ? and semester = ? and for_dept = ? and status = 'Offering'";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ps.setString(2, semester);
            ps.setString(3, dept);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("Course not offered...");
                con.close();
                return 1;
            }
            rs.next();
            String creditedas = "E";
            if (rs.getBoolean(2)) {
                creditedas = "C";
            }
            if (rs.getBoolean(3)) {
                creditedas = "B";
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
            ps.setString(6, creditedas);
            ps.execute();
            con.close();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    int dropRequest(String courseid, String semester) {
        String query = "SELECT course_id, status, grade FROM enrollments WHERE course_id = ? and student_id = ? and semester = ?";
        Date deadline = getAddDropDate(semester);

        if (deadline == null || deadline.compareTo(new Date()) < 0) {
            System.out.println("Unable to process request...");
            return 1;
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
                System.out.println("Course not credited...");
                return 1;
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

//    double calculateSGPA(String semester) {
//        int creds = 0, earnedCreds = 0;
//        creds += getEarnedCredits(semester);
//        earnedCreds += getWeightedEarnedCredits(semester);
//        return earnedCreds / (creds * 1.0);
//    }


    double calculateCGPA() {
        int creds = 0, earnedCreds = 0;
        try {
            String query = "select semester from enrollments where student_id = ? group by semester";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                creds += getEarnedCredits(rs.getString(1));
                earnedCreds += getWeightedEarnedCredits(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return earnedCreds / (creds * 1.0);
    }
}
