package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.repository.BookingRepo;
import com.joysistvi.cinebookapp.repository.BookingRepoImpl;
import com.joysistvi.cinebookapp.service.BookingService;
import com.joysistvi.cinebookapp.service.BookingServiceImpl;
import com.joysistvi.cinebookapp.cliview.BookingView;

public class BookingApp {

    public static void main(String[] args) {

        BookingRepo bookingRepo = new BookingRepoImpl();
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        BookingController bookingController = new BookingController(bookingService);
        BookingView bookingView = new BookingView(bookingController);

        bookingView.showBookingAudit();

    }

}
