package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.UsersRegistrationController;
import com.joysistvi.cinebookapp.model.UsersRegistration;

import java.util.List;
import java.util.Scanner;

public class UsersRegistraionView {

    private final UsersRegistrationController usersRegistrationController;
    private final Scanner scanner;

    public UsersRegistraionView(
            UsersRegistrationController usersRegistrationController,
            Scanner scanner) {

        this.usersRegistrationController = usersRegistrationController;
        this.scanner = scanner;
    }

    public boolean runUserRegistration() {
        handleUsersRegistration(usersRegistrationController, scanner);
        return false;
    }

    private void viewAllUsers() {

        List<UsersRegistration> users =
                usersRegistrationController.handleViewAllUsers();

        printUsers(users);
    }

    private void printUsers(List<UsersRegistration> users) {

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        String border =
                "+" + "-".repeat(6) +
                        "+" + "-".repeat(22) +
                        "+" + "-".repeat(40) +
                        "+" + "-".repeat(14) + "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-20s | %-38s | %-12s |%n",
                "ID",
                "Username",
                "Email",
                "Role"
        );

        System.out.println(border);

        for (UsersRegistration user : users) {

            System.out.printf(
                    "| %-4d | %-20s | %-38s | %-12s |%n",
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }

        System.out.println(border);
    }

    private void handleUsersRegistration(
            UsersRegistrationController usersRegistrationController,
            Scanner scanner) {

        System.out.println();
        System.out.println("========================================================================================");

        System.out.println(
                "   ██████╗██╗███╗   ██╗███████╗██████╗  ██████╗  ██████╗ ██╗  ██╗"
        );
        System.out.println(
                "  ██╔════╝██║████╗  ██║██╔════╝██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝"
        );
        System.out.println(
                "  ██║     ██║██╔██╗ ██║█████╗  ██████╔╝██║   ██║██║   ██║█████╔╝ "
        );
        System.out.println(
                "  ██║     ██║██║╚██╗██║██╔══╝  ██╔══██╗██║   ██║██║   ██║██╔═██╗ "
        );
        System.out.println(
                "  ╚██████╗██║██║ ╚████║███████╗██████╔╝╚██████╔╝╚██████╔╝██║  ██╗"
        );
        System.out.println(
                "   ╚═════╝╚═╝╚═╝  ╚═══╝╚══════╝╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝"
        );

        System.out.println();
        System.out.println(
                "                         MOVIE TICKET BOOKING SYSTEM"
        );

        System.out.println("========================================================================================");

        System.out.println(
                "[ Customer Portal | Registration | CINEBOOK ]"
        );

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println(
                ">>> CREATE CUSTOMER ACCOUNT <<<"
        );

        System.out.println("----------------------------------------------------------------------------------------");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password_hash = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println(
                "[!] Account Role automatically assigned: 'customer'"
        );

        System.out.println(
                "[✓] Checking email availability..."
        );

        boolean success =
                usersRegistrationController.handleRegister(
                        name,
                        email,
                        password_hash
                );

        System.out.println(
                success
                        ? "[✓] Registered successfully! You can now log in."
                        : "[X] Failed to register."
        );

        System.out.println("----------------------------------------------------------------------------------------");
    }
}

        **Inayos ko rin nang konti yung table width** para mas malinis tingnan yung `Email` column. Yung actual registration flow and controller call ay same pa rin.
