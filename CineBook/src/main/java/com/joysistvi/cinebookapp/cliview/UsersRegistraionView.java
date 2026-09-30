package com.joysistvi.cinebookapp.cliview;


import com.joysistvi.cinebookapp.controller.UsersRegistrationController;
import com.joysistvi.cinebookapp.model.UsersRegistration;

import java.util.List;
import java.util.Scanner;

public class UsersRegistraionView {

    private final UsersRegistrationController usersRegistrationController;
    private final Scanner scanner;

    public UsersRegistraionView(UsersRegistrationController usersRegistrationController, Scanner scanner) {
        this.usersRegistrationController = usersRegistrationController;
        this.scanner = scanner;
    }

    public boolean runUsers(){
        viewAllUsers();
        return false;
    }

    private void viewAllUsers() {
        List<UsersRegistration> users = usersRegistrationController.handleViewAllUsers();
        printUsers(users);
    }

    private void printUsers(List<UsersRegistration> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        String border = "+" + "-".repeat(6) + "+" + "-".
                repeat(22) + "+" + "-".repeat(40) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-20s | %-23s | %-11s  |%n", "ID", "Username", "Email", "Role");
        System.out.println(border);

        for (UsersRegistration user : users) {
            System.out.printf("| %-4d | %-20s | %-23s | %-12s |%n", user.getId(),user.getName(), user.getEmail(), user.getRole());
        }

        System.out.println(border);
    }
}
