package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.ShowtimeView;
import com.joysistvi.cinebookapp.controller.ShowtimeController;
import com.joysistvi.cinebookapp.repository.ShowtimeRepo;
import com.joysistvi.cinebookapp.repository.ShowtimeRepoImpl;
import com.joysistvi.cinebookapp.service.ShowtimeService;
import com.joysistvi.cinebookapp.service.ShowtimeServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ShowtimeRepo showtimeRepo = new ShowtimeRepoImpl();

        ShowtimeService showtimeService =
                new ShowtimeServiceImpl(showtimeRepo);

        ShowtimeController showtimeController =
                new ShowtimeController(showtimeService);

        ShowtimeView showtimeView =
                new ShowtimeView(showtimeController, scanner);

        showtimeView.show();

        scanner.close();
    }
}