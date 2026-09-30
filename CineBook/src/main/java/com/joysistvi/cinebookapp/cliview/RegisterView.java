package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.model.Users;
import com.joysistvi.cinebookapp.service.EmailAlreadyExistsException;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

public class RegisterView {

    private static final int WIDTH = 88;
    private static final String DIVIDER = "-".repeat(WIDTH);

    private final UsersController usersController;
    private final Scanner scanner;

    public RegisterView() {
        this(new UsersController(), new Scanner(System.in));
    }

    public RegisterView(UsersController usersController, Scanner scanner) {
        this.usersController = usersController;
        this.scanner = scanner;
    }

    public Optional<Users> show() {
        Header.print();
        System.out.println("[ System: Account Registration ]");
        System.out.println(DIVIDER);
        System.out.println();
        System.out.println(center(">>> CREATE NEW ACCOUNT <<<"));
        System.out.println();

        System.out.print("  Full Name            : ");
        String name = scanner.nextLine().trim();

        System.out.print("  Email Address        : ");
        String email = scanner.nextLine().trim();

        String password = readPassword("  Password             : ");
        String confirmPassword = readPassword("  Confirm Password     : ");
        System.out.println();

        System.out.println(DIVIDER);

        if (!password.equals(confirmPassword)) {
            System.out.println("[X] Passwords do not match!");
            pause();
            return Optional.empty();
        }

        System.out.println("[!] Default role assigned: 'customer'");
        System.out.print("[!] Email uniqueness check against `users` table... ");

        if (usersController.isEmailTaken(email)) {
            System.out.println("FAILED!");
            System.out.println();
            System.out.println("[X] Email is already registered!");
            pause();
            return Optional.empty();
        }
        System.out.println("OK!");

        try {
            Users user = usersController.register(name, email, password);

            System.out.println();
            System.out.println("[✓] Account created successfully!");
            System.out.print("Press [ENTER] to return to Login Screen...");
            scanner.nextLine();

            return Optional.of(user);

        } catch (EmailAlreadyExistsException e) {
            System.out.println();
            System.out.println("[X] Email is already registered!");
            pause();
            return Optional.empty();
        }
    }

    private void pause() {
        System.out.println();
        System.out.print("Press [ENTER] to continue...");
        scanner.nextLine();
    }

    private String readPassword(String prompt) {
        Console console = System.console();
        if (console == null) {
            System.out.print(prompt);
            return scanner.nextLine();
        }

        char[] input = console.readPassword(prompt);
        return input == null ? "" : new String(input);
    }

    private String center(String text) {
        int padding = Math.max((WIDTH - text.length()) / 2, 0);
        return " ".repeat(padding) + text;
    }
}
