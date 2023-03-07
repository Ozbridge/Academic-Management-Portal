package org.soft;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FacultyServices extends AcademicServices {
    String facultyID;
    String dept;

    FacultyServices(String facultyID, String dept) {
        this.facultyID = facultyID;
        this.dept = dept;
    }

    int addOffering(String course_id, String semester, String[] for_dept, boolean[] is_core, double cgpa) {
        try {
            String query = "select active from courses where id = ?";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, course_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (!rs.getBoolean(1)) {
                    System.out.println("Course not in Catalog");
                    return 1;
                }
            } else {
                System.out.println("Course not in Catalog");
                return 1;
            }
            query = "INSERT INTO offerings values (?, ?, ?, ?, ?, ?)";
            for (int i = 0; i < for_dept.length; i++) {
                ps = con.prepareStatement(query);
                ps.setString(1, course_id);
                ps.setString(2, "Offering");
                ps.setString(3, semester);
                ps.setString(4, for_dept[i]);
                ps.setBoolean(5, is_core[i]);
                ps.setDouble(6, cgpa);
                ps.execute();
            }
            query = "INSERT INTO offering_instructors values (?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, course_id);
            ps.setString(2, semester);
            ps.setString(3, facultyID);
            ps.setBoolean(4, true);
            ps.execute();
            con.close();
            return 0;
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
            con.close();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private ArrayList<String> getOfferedCourses(String semester) {
        String query = "select course_id from offering_instructors where instructor_id = ? and semester = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, facultyID);
            ps.setString(2, semester);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            con.close();
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private int _uploadGrade(String studentid, String course_id, String semester, String grade) {
        try {
            Connection con = DatabaseService.getConnection();
            String query = "UPDATE enrollments " +
                    "SET grade = ?  " +
                    "WHERE student_id = ? and course_id = ? and semester = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, grade);
            ps.setString(2, studentid);
            ps.setString(3, course_id);
            ps.setString(4, semester);
            ps.execute();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int uploadGrades(String courseid, String semester, File fd) {
        try {
            ArrayList<String> offeredCourses = getOfferedCourses(semester);
            System.out.println(offeredCourses.get(0));
            if (offeredCourses == null || !offeredCourses.contains(courseid)) {
                System.out.println("Unable to upload grades");
                return 1;
            }
            if (fd == null)
                throw new FileNotFoundException();
            FileReader fileReader = new FileReader(fd);
            CSVReader csvReader = new CSVReader(fileReader);
            String record[];
            while ((record = csvReader.readNext()) != null) {
                _uploadGrade(record[0], courseid, semester, record[1]);
            }
            return 0;
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
        return null;
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
