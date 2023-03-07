package org.soft;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Scanner;

public class AdminServices extends AcademicServices {
    int addCourse(String title, String code, String dept, int credits, boolean isbtp) {
        String query = "INSERT INTO COURSES VALUES (?, ?, ?, ?, true, ?) on conflict (id) do update SET name = EXCLUDED.name, dept = excluded.dept, credits = excluded.credits, active = excluded.active, isbtp = excluded.isbtp";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, code);
            ps.setString(3, dept);
            ps.setInt(4, credits);
            ps.setBoolean(5, isbtp);
            ps.execute();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the number of prerequisites: ");
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                System.out.println("Enter pre-requisite course id: ");
                addPrerequisites(code, sc.next());
            }
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    int addPrerequisites(String course, String prereq) {
        String query = "INSERT INTO PREREQUISITES VALUES (?, ?)";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course);
            ps.setString(2, prereq);
            ps.execute();
            ps.close();
            return 0;
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
        String query = "UPDATE courses set active = false WHERE id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, code);
            ps.execute();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
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
        return null;
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
            String currentPath = new java.io.File(".").getCanonicalPath();
            currentPath = currentPath + "/transcripts/" + studentid + "_" + semester + ".txt";
            File fd = new File(currentPath);
            FileWriter fw = new FileWriter(fd);
            fw.write(ans.toString());
            fw.close();
            System.out.println("Transcript save to : " + currentPath);
            return ans.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    int getContactDetails(String userid) {
        String query = "SELECT username, phone, email from users where userid = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();
            String out = "No user found!";
            if (rs.next()) {
                out = "Name: " + rs.getString(1) + " Phone: " + rs.getString(2) + " Email: " + rs.getString(3) + "\n";
                con.close();
                System.out.println(out);
                return 0;
            } else {
                con.close();
                System.out.println(out);
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }
}
