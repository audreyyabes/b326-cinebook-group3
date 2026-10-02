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

    public boolean runUserRegistration(){
        handleUsersRegistration(usersRegistrationController, scanner);
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

    private void handleUsersRegistration(UsersRegistrationController usersRegistrationController, Scanner scanner){
        clearScreen();

        System.out.println("""
========================================================================================
                      ____ _____ _  _ _____ ____  ____  ____  _  _
                     / ___|_   _| || | ____| __ )/ ___|/ ___|| || |
                    | |     | | | || |  _| |  _ \\ |  /| |   | || |
                    | |___  | | | || | |___| |_) | |__| |___| __ |
                     \\____| |_| |_||_|_____|____/\\____|\\____|_||_|
========================================================================================
[ Portal: Customer Registration ]
----------------------------------------------------------------------------------------
                         >>> CREATE CUSTOMER ACCOUNT <<<""");
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Password: ");
        String password_hash = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("[!] Account Role automatically assigned: 'customer'");
        System.out.println("[✓] Checking email availability...");

        boolean success = usersRegistrationController.handleRegister(name, email, password_hash);
        System.out.println(success
                ? "Registered successfully! You can now log in."
                : "Failed to register.");
    }

    private static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback if the process can't be started
            System.out.println("\n".repeat(50));
        }


    }
}