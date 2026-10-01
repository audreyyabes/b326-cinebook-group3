package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.model.Users;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

public class LoginView {

    private static final int WIDTH = 88;
    private static final String DIVIDER = "-".repeat(WIDTH);

    private final UsersController usersController;
    private final Scanner scanner;

    public LoginView() {
        this(new UsersController(), new Scanner(System.in));
    }

    public LoginView(UsersController usersController, Scanner scanner) {
        this.usersController = usersController;
        this.scanner = scanner;
    }

    public Optional<Users> show() {
        Header.print();
        System.out.println("[ System: Authentication Screen ]");
        System.out.println(DIVIDER);
        System.out.println();
        System.out.println(center(">>> USER LOGIN <<<"));
        System.out.println();

        System.out.print("  Enter Email    : ");
        String email = scanner.nextLine().trim();

        String password = readPassword("  Enter Password : ");
        System.out.println();

        System.out.println(DIVIDER);
        System.out.println("[ Authenticative validation via bcrypt password_hash ]");
        System.out.print("Authenticating... ");

        Optional<Users> user = usersController.login(email, password);

        if (user.isPresent()) {
            System.out.println("SUCCESS! Welcome back, " + user.get().getName() + ".");
            System.out.println();
            System.out.print("Press [ENTER] to proceed to Customer Portal...");
            scanner.nextLine();
        } else {
            System.out.println("FAILED! Invalid email or password.");
            System.out.println();
            System.out.print("Press [ENTER] to continue...");
            scanner.nextLine();
        }

        return user;
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
