package org.soft;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AuthService as = new AuthService();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String user, password;
            System.out.println("Username:");
            user = sc.next();
            System.out.println("Password:");
            password = sc.next();
            String[] temp = as.login(user.trim(), password.trim());
            if (temp.length == 0) {
                System.out.println("Try again...");
                continue;
            }
            Menu menu = null;
            switch (temp[1]) {
                case "STU":
                    menu = new StudentMenu(temp[0], temp[2]);
                    break;
                case "FAC":
                    menu = new FacultyMenu(temp[0], temp[2]);
                    break;
                case "ADM":
                    menu = new AdminMenu();
                    break;
                default:
                    System.out.println("Error occurred, try again...");
            }
            while (true) {
                menu.showOptions();
                menu.doStuff(sc.next());
                System.out.println("Enter 'logout' to logout or anything else to continue...");
                if (sc.next().equals("logout"))
                    break;
            }
            System.out.println("Enter 'quit' to quit or anything else to continue...");
            if (sc.next().equals("quit"))
                break;
        }
//        DatabaseService.cpds.close();
    }

}