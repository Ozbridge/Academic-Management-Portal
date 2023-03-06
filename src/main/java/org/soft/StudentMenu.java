package org.soft;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu implements Menu {

    StudentServices studentServices;

    public StudentMenu(String student, String dept) {
        this.studentServices = new StudentServices(student, dept);
    }


    public void showOptions() {
        System.out.println("Please pick an option.\n" +
                "1. Get total registered credits.\n" +
                "2. Get completed courses.\n" +
                "3. Credit a course.\n" +
                "4. Drop a course.\n" +
                "5. Withdraw a course.\n" +
                "6. Get CGPA\n" +
                "7. See Offerings\n" +
                "8. Update Contact Details");
    }

    public void doStuff(String input) {
        Scanner sc = new Scanner(System.in);
        switch (input) {
            case "1":
                System.out.println("Enter semester: ");
                int temp = studentServices.getRegisteredCredits(sc.next().trim());
                System.out.println("Registered Credits are: " + temp);
                break;
            case "2":
                ArrayList<String> courseList = studentServices.getCompletedCourses();
                for (String course : courseList) {
                    System.out.println(course);
                }
                break;
            case "3":
                String course, semester, option;
                System.out.println("Enter course ID: ");
                course = sc.next();
                System.out.println("Enter semester: ");
                semester = sc.next();
                studentServices.creditRequest(course, semester);
                break;
            case "4":
                System.out.println("Enter course ID: ");
                course = sc.next();
                System.out.println("Enter semester: ");
                semester = sc.next();
                studentServices.dropRequest(course.trim(), semester.trim());
                break;
            case "5":
                System.out.println("Enter course ID: ");
                course = sc.next();
                System.out.println("Enter semester: ");
                semester = sc.next();
                studentServices.withdrawRequest(course, semester);
                break;
            case "6":
                System.out.println("CGPA: " + studentServices.calculateCGPA());
                break;
            case "7":
                System.out.println("Enter semester: ");
                semester = sc.next();
                studentServices.seeOfferings(semester);
                break;
            case "8":
                studentServices.updateContactDetails(studentServices.studentId);
                break;
            default:
                System.out.println("Invalid input, try again...");
        }
    }

}
