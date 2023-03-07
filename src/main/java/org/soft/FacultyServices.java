package org.soft;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                //noinspection JpaQueryApiInspection
                ps.setString(2, "Offering");
                //noinspection JpaQueryApiInspection
                ps.setString(3, semester);
                //noinspection JpaQueryApiInspection
                ps.setString(4, for_dept[i]);
                //noinspection JpaQueryApiInspection
                ps.setBoolean(5, is_core[i]);
                //noinspection JpaQueryApiInspection
                ps.setDouble(6, cgpa);
                ps.execute();
            }
            query = "INSERT INTO offering_instructors values (?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, course_id);
            //noinspection JpaQueryApiInspection
            ps.setString(2, semester);
            //noinspection JpaQueryApiInspection
            ps.setString(3, facultyID);
            //noinspection JpaQueryApiInspection
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

        if (!getOfferedCourses(semester).contains(course_id)) {
            System.out.println("Unalbe to process request...");
            return 1;
        }
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
//        String query = "select course_id from offering_instructors where instructor_id = ? and semester = ?";
        String query = "select offerings.course_id from offerings, offering_instructors where offerings.course_id = offering_instructors.course_id and offering_instructors.semester = offerings.semester and offering_instructors.instructor_id =? and offerings.semester = ? and status != 'Cancelled'";

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

    int seeSelfOfferings(String semester) {

        try {
            String query = "select offerings.course_id, name, for_dept, is_core from offerings, courses, offering_instructors where offerings.course_id = id and offerings.semester = ? and offering_instructors.course_id = offerings.course_id and offering_instructors.semester = offerings.semester and offering_instructors.instructor_id =? and status != 'Cancelled'";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, semester);
            ps.setString(2, facultyID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Course ID: " + rs.getString(1) +
                        " Title: " + rs.getString(2) +
                        " Dept: " + rs.getString(3) + " Type: " + ((rs.getBoolean(4)) ? "Core" : "Elective"));
            }
            con.close();
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }
}
