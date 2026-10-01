package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.UserBookingView;
import com.joysistvi.cinebookapp.controller.UserBookingController;
import com.joysistvi.cinebookapp.service.UserBookingService;
import com.joysistvi.cinebookapp.service.UserBookingServiceImpl;

public class UserBookingApp {

    public static void main(String[] args) {

        UserBookingService userBookingService = new UserBookingServiceImpl();
        UserBookingController userBookingController = new UserBookingController(userBookingService);
        UserBookingView userBookingView = new UserBookingView(userBookingController);

        int userId = 2;

        userBookingView.display(userId);
    }
}
