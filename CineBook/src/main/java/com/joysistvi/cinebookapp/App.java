package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.UsersView;
import com.joysistvi.cinebookapp.config.DBConnection;
import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.repository.UsersRepo;
import com.joysistvi.cinebookapp.repository.UsersRepoImpl;
import com.joysistvi.cinebookapp.service.UsersService;
import com.joysistvi.cinebookapp.service.UsersServiceImpl;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBConnection dbConnection = new DBConnection();

        UsersRepo usersRepo = new UsersRepoImpl(dbConnection);
        UsersService usersService = new UsersServiceImpl(usersRepo);
        UsersController usersController = new UsersController(usersService);
        UsersView usersView = new UsersView(usersController, scanner);

        usersView.runUsers();

    }
}
