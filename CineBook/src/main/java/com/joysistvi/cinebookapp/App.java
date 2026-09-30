package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.UsersRegistraionView;
import com.joysistvi.cinebookapp.config.DBConnection;
import com.joysistvi.cinebookapp.controller.UsersRegistrationController;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepo;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepoImpl;
import com.joysistvi.cinebookapp.service.UsersRegistrationService;
import com.joysistvi.cinebookapp.service.UsersRegistrationServiceImpl;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection();

        UsersRegistrationRepo usersRegistrationRepo = new UsersRegistrationRepoImpl(dbConnection);
        UsersRegistrationService usersRegistrationService = new UsersRegistrationServiceImpl(usersRegistrationRepo);
        UsersRegistrationController usersRegistrationController = new UsersRegistrationController(usersRegistrationService);
        UsersRegistraionView usersRegistraionView = new UsersRegistraionView(usersRegistrationController, scanner);

        usersRegistraionView.runUsers();

    }
}
