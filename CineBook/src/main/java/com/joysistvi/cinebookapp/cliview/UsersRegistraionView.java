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

    public boolean runUserRegistration() {
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
        CliLayout.table(List.of("ID", "Username", "Email", "Role"), users.stream()
            .map(user -> List.of(user.getId(), user.getName(), user.getEmail(), user.getRole()))
            .toList());
    }

    private void handleUsersRegistration(UsersRegistrationController usersRegistrationController, Scanner scanner) {
        Header.print();
        CliLayout.println("[ Portal: Customer Registration ]");
        CliLayout.println("-".repeat(88));
        CliLayout.println();
        CliLayout.println("                         >>> CREATE CUSTOMER ACCOUNT <<<");
        CliLayout.println();
        CliLayout.print("Full Name        : ");
        String name = scanner.nextLine().trim();
        CliLayout.print("Email Address    : ");
        String email = scanner.nextLine().trim();
        CliLayout.print("Password         : ");
        String password_hash = scanner.nextLine();
        CliLayout.print("Confirm Password : ");
        String confirmation = scanner.nextLine();

        CliLayout.println();
        CliLayout.println("-".repeat(88));
        CliLayout.println("[!] Account Role automatically assigned: 'customer'");
        if (!password_hash.equals(confirmation)) {
            CliLayout.println("[X] Passwords do not match. No account was created.");
            return;
        }
        CliLayout.println("[✓] Checking email availability...");

        boolean success = usersRegistrationController.handleRegister(name, email, password_hash);
        CliLayout.println(success
                ? "[✓] Customer account successfully registered."
                : "[X] Registration failed. Check whether the email is already in use.");
    }
}