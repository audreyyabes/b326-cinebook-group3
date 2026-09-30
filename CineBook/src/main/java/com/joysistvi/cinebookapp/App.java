package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.UsersRegistraionView;
import com.joysistvi.cinebookapp.controller.UsersRegistrationController;
import com.joysistvi.cinebookapp.database.DatabaseBootstrap;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.database.DatabaseMigration;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepo;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepoImpl;
import com.joysistvi.cinebookapp.service.UsersRegistrationService;
import com.joysistvi.cinebookapp.service.UsersRegistrationServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DatabaseBootstrap bootstrap = new DatabaseBootstrap(); // Create database upon running the application
        DatabaseMigration migration = new DatabaseMigration(); // Create tables upon running the application
        DatabaseConnection databaseConnection = new DatabaseConnection();

        UsersRegistrationRepo usersRegistrationRepo = new UsersRegistrationRepoImpl(databaseConnection);
        UsersRegistrationService usersRegistrationService = new UsersRegistrationServiceImpl(usersRegistrationRepo);
        UsersRegistrationController usersRegistrationController = new UsersRegistrationController(usersRegistrationService);
        UsersRegistraionView usersRegistraionView = new UsersRegistraionView(usersRegistrationController, scanner);

    }
}
