package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.Header;
import com.joysistvi.cinebookapp.cliview.LoginView;
import com.joysistvi.cinebookapp.cliview.RegisterView;
import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.database.AdminAccountInitializer;
import com.joysistvi.cinebookapp.database.DatabaseBootstrap;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.database.DatabaseMigration;
import com.joysistvi.cinebookapp.model.Users;

import java.util.Optional;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        DatabaseBootstrap bootstrap = new DatabaseBootstrap(); // Create database upon running the application
        DatabaseMigration migration = new DatabaseMigration(); // Create tables upon running the application
        DatabaseConnection databaseConnection = new DatabaseConnection();
        AdminAccountInitializer adminAccountInitializer = new AdminAccountInitializer(); // Create admin account upon running the application

        bootstrap.createDatabaseIfNotExists();
        migration.migrate();
        databaseConnection.testConnection();
        adminAccountInitializer.run();

        UsersController usersController = new UsersController();
        Scanner scanner = new Scanner(System.in);
        LoginView loginView = new LoginView(usersController, scanner);
        RegisterView registerView = new RegisterView(usersController, scanner);

        Optional<Users> session = Optional.empty();
        while (session.isEmpty()) {
            Header.print();
            System.out.println();
            System.out.println("\t\t\t[1] Login");
            System.out.println("\t\t\t[2] Register");
            System.out.println("\t\t\t[3] Exit");
            System.out.println();
            System.out.print("\t\t\tSelect an option: ");
            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> session = loginView.show();
                case "2" -> registerView.show();
                case "3" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> {
                    System.out.println("Invalid option, please try again.");
                    System.out.print("Press [ENTER] to continue...");
                    scanner.nextLine();
                }
            }
        }
    }
}
