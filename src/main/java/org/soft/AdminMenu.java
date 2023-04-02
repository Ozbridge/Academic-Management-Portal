package org.soft;

import com.sun.tools.attach.AgentInitializationException;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminMenu implements Menu {
    AdminServices adminServices;
    Scanner sc = new Scanner(System.in);
    String semester, course, path, studentid, title, dept;
    int n;

    public AdminMenu() {
        this.adminServices = new AdminServices();
    }


    public void showOptions() {
        System.out.println("Please pick an option.\n" +
                "1. Add Course to Catalog.\n" +
                "2. Remove course in Catalog.\n" +
                "3. Generate Transcript.\n" +
                "4. Check if a student can graduate.\n" +
                "5. Get Student's grade in a course.\n" +
                "6. Get Contact Details of a user.\n" +
                "7. View Catalog");
    }

    private void addCourseToCatalog() {
        sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter course title: ");
        sc.nextLine();
        title = sc.nextLine();
        System.out.println("Enter offering department code: ");
        dept = sc.next();
        System.out.println("Enter credits: ");
        n = sc.nextInt();
        System.out.println("Do these count as BTP credits, enter true/false");
        boolean ip = (sc.next().equals("true")) ? true : false;
        adminServices.addCourse(title, course, dept, n, ip);
    }

    private void removeCourseInCatalog() {
        sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        adminServices.removeCourse(course);
    }

    private void generateTranscript() {
        sc = new Scanner(System.in);
        System.out.println("StudentID: ");
        studentid = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        System.out.println(adminServices.generateTranscript(studentid, semester));
    }

    private void checkIfStudentCanGraduate() {
        sc = new Scanner(System.in);
        System.out.println("Enter Student ID: ");
        studentid = sc.next();
        System.out.println(adminServices.canGraduate(studentid));
    }

    private void getStudentGradeInCourse() {
        sc = new Scanner(System.in);
        System.out.println("StudentID: ");
        studentid = sc.next();
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        System.out.println("The grade is: " + adminServices.getGrade(studentid, course, semester));
    }

    private void getContactDetails() {
        sc = new Scanner(System.in);
        System.out.println("Enter UserID to get contact details: ");
        studentid = sc.next();
        adminServices.getContactDetails(studentid);
    }

    private void viewCatalog() {
        try {
            adminServices.viewCatalog();
        } catch (Exception ignored) {
        }
    }

    public void doStuff(String input) {


        switch (input) {
            case "1":
                addCourseToCatalog();
                break;
            case "2":
                removeCourseInCatalog();
                break;
            case "3":
                generateTranscript();
                break;
            case "4":
                checkIfStudentCanGraduate();
                break;
            case "5":
                getStudentGradeInCourse();
                break;
            case "6":
                getContactDetails();
                break;
            case "7":
                viewCatalog();
                break;
            default:
                System.out.println("Invalid input, try again...");
        }
    }
}
