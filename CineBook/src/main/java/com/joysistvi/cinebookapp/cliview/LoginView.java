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
        CliLayout.println("[ Portal: Account Login ]");
        CliLayout.println(DIVIDER);
        CliLayout.println();
        CliLayout.println(center(">>> USER LOGIN <<<"));
        CliLayout.println();

        CliLayout.print("  Enter Email    : ");
        String email = scanner.nextLine().trim();

        String password = readPassword("  Enter Password : ");
        CliLayout.println();

        CliLayout.println(DIVIDER);
        CliLayout.print("Authenticating... ");

        Optional<Users> user = usersController.login(email, password);

        if (user.isPresent()) {
            CliLayout.println("[✓] Authentication successful!");
            CliLayout.println("[i] Role detected: " + user.get().getRole().toUpperCase());
            CliLayout.println();
            CliLayout.print("Press [ENTER] to continue to "
                    + ("admin".equalsIgnoreCase(user.get().getRole()) ? "Admin" : "Customer")
                    + " Portal...");
            scanner.nextLine();
        } else {
            CliLayout.println("FAILED! Invalid email or password.");
            CliLayout.println();
            CliLayout.print("Press [ENTER] to continue...");
            scanner.nextLine();
        }

        return user;
    }

    private String readPassword(String prompt) {
        Console console = System.console();
        if (console == null) {
            CliLayout.print(prompt);
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
