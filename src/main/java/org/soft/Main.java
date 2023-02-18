package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AdminServices ad = new AdminServices();
        FacultyServices fs = new FacultyServices("FCS001");
        StudentServices ss = new StudentServices("rishabhjain", "CSE");
//        ss.creditRequest("CS503", "2023-W", "");
        ss.dropRequest("CS503", "2023-W");
//        ad.addCourse("Machine Learning", "CS503", "CSE", 4);
//        fs.addOffering("CS503", "2023-W", new String[]{"CSE", "MNC", "EE"}, new boolean[]{true, true, false});
//        fs.removeOffering("CS503", "2023-W");
//        ad.removeCourse("CS503");
        DatabaseService.cpds.close();
//        ad.removeCourse();
    }

}