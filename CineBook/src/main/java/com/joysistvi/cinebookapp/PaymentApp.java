package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.PaymentView;
import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.controller.BookingSeatsController;
import com.joysistvi.cinebookapp.controller.PaymentController;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.repository.BookingRepo;
import com.joysistvi.cinebookapp.repository.BookingRepoImpl;
import com.joysistvi.cinebookapp.repository.BookingSeatsRepo;
import com.joysistvi.cinebookapp.repository.BookingSeatsRepoImpl;
import com.joysistvi.cinebookapp.service.BookingSeatsService;
import com.joysistvi.cinebookapp.service.BookingSeatsServiceImpl;
import com.joysistvi.cinebookapp.service.BookingService;
import com.joysistvi.cinebookapp.service.BookingServiceImpl;
import com.joysistvi.cinebookapp.service.PaymentService;
import com.joysistvi.cinebookapp.service.PaymentServiceImpl;

import java.util.List;
import java.util.Scanner;

public class PaymentApp {

    public static void main(String[] args) {

        BookingRepo bookingRepo = new BookingRepoImpl();
        BookingService bookingService = new BookingServiceImpl(bookingRepo);
        BookingController bookingController = new BookingController(bookingService);

        BookingSeatsRepo bookingSeatsRepo = new BookingSeatsRepoImpl(new DatabaseConnection());
        BookingSeatsService bookingSeatsService = new BookingSeatsServiceImpl(bookingSeatsRepo);
        BookingSeatsController bookingSeatsController = new BookingSeatsController(bookingSeatsService);

        PaymentService paymentService = new PaymentServiceImpl();
        PaymentController paymentController = new PaymentController(paymentService);

        Scanner scanner = new Scanner(System.in);
        PaymentView paymentView = new PaymentView(bookingController, bookingSeatsController, paymentController, scanner);

        // Sample booking context, matching the seeded demo data (Audrey Yabes, Avengers: Endgame,
        // showtime 1 @ Cinema 1, seats A3 and A4). In the full app this would come from the
        // movie, showtime, and seat-selection screens instead of being hardcoded here.
        paymentView.show(
                "Audrey Yabes",
                2,
                1,
                "Avengers: Endgame",
                "Cinema 1 (CineBook Mall - Quezon City)",
                "2026-10-01 @ 10:00 AM - 01:01 PM",
                List.of("A3", "A4"),
                List.of(3, 4),
                350.00
        );
    }
}
