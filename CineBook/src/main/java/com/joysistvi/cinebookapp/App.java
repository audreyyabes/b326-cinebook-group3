package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.Header;
import com.joysistvi.cinebookapp.cliview.LoginView;
import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.database.AdminAccountInitializer;
import com.joysistvi.cinebookapp.database.DatabaseBootstrap;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.database.DatabaseMigration;
import com.joysistvi.cinebookapp.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        DatabaseBootstrap bootstrap = new DatabaseBootstrap(); // Create database upon running the application
        DatabaseMigration migration = new DatabaseMigration(); // Create tables upon running the application
        DatabaseConnection databaseConnection = new DatabaseConnection();
        AdminAccountInitializer adminAccountInitializer = new AdminAccountInitializer(); // Create admin account upon running the application

        try {
            bootstrap.createDatabaseIfNotExists();
            migration.migrate();
            databaseConnection.testConnection();
            adminAccountInitializer.run();
        } catch (RuntimeException e) {
            logger.error("Application startup encountered an error", e);
            System.out.println("[!] Startup warning: some features may not work. Check the logs for details.");
        }

        UsersController usersController = new UsersController();
        Scanner scanner = new Scanner(System.in);
        LoginView loginView = new LoginView(usersController, scanner);

        Optional<Users> session = Optional.empty();
        while (session.isEmpty()) {
            Header.print();
            System.out.println();
            System.out.println("\t\t\t[1] Login");
            System.out.println("\t\t\t[2] Exit");
            System.out.println();
            System.out.print("\t\t\tSelect an option: ");
            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> session = safeShow(loginView::show, scanner);
                case "2" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> {
                    System.out.println("Invalid option, please try again.");
                    pause(scanner);
                }
            }
        }
    }

    private static Optional<Users> safeShow(Supplier<Optional<Users>> view, Scanner scanner) {
        try {
            return view.get();
        } catch (RuntimeException e) {
            logger.error("Unexpected error while handling the request", e);
            System.out.println("[X] An unexpected error occurred. Please try again.");
            pause(scanner);
            return Optional.empty();
        }
    }

    private static void pause(Scanner scanner) {
        try {
            System.out.print("Press [ENTER] to continue...");
            scanner.nextLine();
        } catch (RuntimeException e) {
            // Input stream unavailable (e.g. stdin closed); nothing more to do.
        }
    }
}
