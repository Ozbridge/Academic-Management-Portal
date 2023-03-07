package org.soft;

import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FacultyMenu implements Menu {
    FacultyServices facultyServices;
    Scanner sc = new Scanner(System.in);
    String semester, course, path, studentid;
    int n;

    public FacultyMenu(String facultyid, String dept) {
        this.facultyServices = new FacultyServices(facultyid, dept);
    }


    public void showOptions() {
        System.out.println("Please pick an option.\n" +
                "1. Add Offering.\n" +
                "2. Remove Offering.\n" +
                "3. Upload Grades.\n" +
                "4. See enrolled Students.\n" +
                "5. Get Student grade in a course.\n" +
                "6. Update Contact Details.\n" +
                "7. Get contact details\n" +
                "8. See your Offerings");
    }

    private void addOffering() {
        sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        System.out.println("Enter the number of departments to offer the course to: ");
        n = sc.nextInt();
        String[] for_dept = new String[n];
        boolean[] is_core = new boolean[n];
        System.out.println("Enter department followed by true if it is a core course for that department otherwise false");
        for (int i = 0; i < n; i++) {
            System.out.println("Enter space seperated department code and true/false");
            for_dept[i] = sc.next().trim();
            is_core[i] = sc.next().trim().equals("true");
        }
        System.out.println("Enter the CGPA criteria for this course, not no criteria enter 0: ");
        Double cgpa = sc.nextDouble();
        facultyServices.addOffering(course, semester, for_dept, is_core, cgpa);
    }

    private void removeOffering() {
        sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        facultyServices.removeOffering(course, semester);
    }

    private void uploadGrades() {
        sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        System.out.println("Enter CSV absolute path");
        path = sc.next();
        File fd = new File(path);
        facultyServices.uploadGrades(course, semester, fd);
    }

    private void seeEnrolledStudents() {
        sc = new Scanner(System.in);
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        facultyServices.getEnrollmentList(course.trim(), semester.trim());
    }

    private void getStudentGrade() {
        sc = new Scanner(System.in);
        System.out.println("StudentID: ");
        studentid = sc.next();
        System.out.println("Enter course ID: ");
        course = sc.next();
        System.out.println("Enter semester: ");
        semester = sc.next();
        System.out.println("The grade is: " + facultyServices.getGrade(studentid, course, semester));
    }

    public void doStuff(String input) {


        switch (input) {
            case "1" -> addOffering();
            case "2" -> removeOffering();
            case "3" -> uploadGrades();
            case "4" -> seeEnrolledStudents();
            case "5" -> getStudentGrade();
            case "6" -> facultyServices.updateContactDetails(facultyServices.facultyID);
            case "7" -> {
                sc = new Scanner(System.in);
                System.out.println("Enter UserID to get contact details: ");
                studentid = sc.next();
                facultyServices.getContactDetails(studentid);
            }
            case "8" -> {
                sc = new Scanner(System.in);
                System.out.println("Enter semester: ");
                semester = sc.next();
                facultyServices.seeSelfOfferings(semester);
            }
            default -> System.out.println("Invalid input, try again...");
        }
    }
}
