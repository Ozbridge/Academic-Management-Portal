package org.soft;

import java.security.PrivilegedAction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu implements Menu {

    StudentServices studentServices;
    String course, semester, option;

    public StudentMenu(String student, String dept) {
        this.studentServices = new StudentServices(student, dept);
    }


    public void showOptions() {
        System.out.println("Please pick an option.\n" +
                "1. Get registered courses.\n" +
                "2. Get completed courses.\n" +
                "3. Credit a course.\n" +
                "4. Drop a course.\n" +
                "5. Get CGPA\n" +
                "6. See Offerings\n" +
                "7. Update Contact Details\n");
    }

    private void getRegisteredCourses() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter semester: ");
        String sem = sc.next();
        studentServices.getRegisteredCourses(sem);
    }

    private void getCompletedCourseList() throws SQLException {
        ArrayList<String> courseList = studentServices.getCompletedCourses();
        for (String course : courseList) {
            System.out.println(course);
        }
    }

    private void creditACourse() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
//        System.out.println(course);
        System.out.println("Enter semester: ");
        semester = sc.next();
        studentServices.creditRequest(course, semester);
    }

    private void dropACourse() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        studentServices.dropRequest(course.trim(), semester.trim());
    }

    private void seeOfferings() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter semester: ");
        semester = sc.next();
        studentServices.seeOfferings(semester);
    }

    public void doStuff(String input) {
        try {
            switch (input) {
                case "1" -> getRegisteredCourses();
                case "2" -> getCompletedCourseList();
                case "3" -> creditACourse();
                case "4" -> dropACourse();
                case "5" -> System.out.println("CGPA: " + studentServices.calculateCGPA());
                case "6" -> seeOfferings();
                case "7" -> studentServices.updateContactDetails(studentServices.studentId);
                default -> System.out.println("Invalid input, try again...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
