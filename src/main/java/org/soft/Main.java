package org.soft;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Date());
        AdminServices ad = new AdminServices();
        FacultyServices fs = new FacultyServices("FCS001");
        StudentServices ss = new StudentServices("rishabhjain", "CSE");
        AuthService as = new AuthService();


//        ad.addCourse("Machine Learning", "CS503", "CSE", 4);
//        ad.removeCourse("CS503");
        System.out.println(ad.generateTranscript("rishabhjain", "2023-W"));
//        System.out.println(ad.getGrade("rishabhjain", "CS503", "2023-W"));
//        System.out.println(ad.canGraduate("rishabhjain"));
//
//        fs.addOffering("CS503", "2022-S", new String[]{"CSE", "MNC", "EE"}, new boolean[]{true, true, false});
//        fs.removeOffering("CS503", "2023-W");
//        fs.uploadGrades("CS503", "2023-W", new File("/Users/rishabhjain/IdeaProjects/softE/SoftE/datatemp/gradeCS503.csv"));
//
//
//        ss.creditRequest("CS503", "2023-W", "");
//        ss.dropRequest("CS201", "2022-S");
//        System.out.println(ss.hasCompletedPrerequisites("CS503"));
//        System.out.println(ss.calculateCGPA("2023-W"));
//
//
//        String[] temp = as.login("Rishabh Jain", "strongpassword");
//        for (String c : temp)
//            System.out.println(c);
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            String ip;
//            ip = sc.next();
//        }

        DatabaseService.cpds.close();
    }

}