package com.joysistvi.cinebookapp.cliview;


import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.model.Users;

import java.util.List;
import java.util.Scanner;

public class UsersView {

    private final UsersController usersController;
    private final Scanner scanner;

    public UsersView(UsersController usersController, Scanner scanner) {
        this.usersController = usersController;
        this.scanner = scanner;
    }

    public boolean runUsers(){
        viewAllUsers();
        return false;
    }

    private void viewAllUsers() {
        List<Users> users = usersController.handleViewAllUsers();
        printUsers(users);
    }

    private void printUsers(List<Users> users) {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        String border = "+" + "-".repeat(6) + "+" + "-".
                repeat(22) + "+" + "-".repeat(40) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-20s | %-23s | %-11s  |%n", "ID", "Username", "Email", "Role");
        System.out.println(border);

        for (Users user : users) {
            System.out.printf("| %-4d | %-20s | %-23s | %-12s |%n", user.getId(),user.getName(), user.getEmail(), user.getRole());
        }

        System.out.println(border);
    }
}
