package org.soft;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class FacultyServices extends AcademicServices {
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
            con.close();

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

    private void _uploadGrade(String studentid, String course_id, String semester, String grade) {
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
    }

    int uploadGrades(String courseid, String semester, File fd) {
        try {
            FileReader fileReader = new FileReader(fd);
            CSVReader csvReader = new CSVReader(fileReader);
            String record[];
            while ((record = csvReader.readNext()) != null) {
                _uploadGrade(record[0], courseid, semester, record[1]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }
}
