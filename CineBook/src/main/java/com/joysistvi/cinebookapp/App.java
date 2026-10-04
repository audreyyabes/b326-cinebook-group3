package com.joysistvi.cinebookapp;

import com.joysistvi.cinebookapp.cliview.CinemaCliView;
import com.joysistvi.cinebookapp.cliview.LoginView;
import com.joysistvi.cinebookapp.cliview.UsersRegistraionView;
import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.controller.BookingSeatsController;
import com.joysistvi.cinebookapp.controller.MoviesController;
import com.joysistvi.cinebookapp.controller.PaymentController;
import com.joysistvi.cinebookapp.controller.SeatMapController;
import com.joysistvi.cinebookapp.controller.ShowtimeController;
import com.joysistvi.cinebookapp.controller.TheaterController;
import com.joysistvi.cinebookapp.controller.UserBookingController;
import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.controller.UsersRegistrationController;
import com.joysistvi.cinebookapp.database.AdminAccountInitializer;
import com.joysistvi.cinebookapp.database.DatabaseBootstrap;
import com.joysistvi.cinebookapp.database.DatabaseConnection;
import com.joysistvi.cinebookapp.database.CinemaDataSeeder;
import com.joysistvi.cinebookapp.database.DatabaseMigration;
import com.joysistvi.cinebookapp.repository.BookingRepoImpl;
import com.joysistvi.cinebookapp.repository.BookingSeatsRepoImpl;
import com.joysistvi.cinebookapp.repository.MoviesRepoImpl;
import com.joysistvi.cinebookapp.repository.SeatMapRepoImpl;
import com.joysistvi.cinebookapp.repository.ShowtimeRepoImpl;
import com.joysistvi.cinebookapp.repository.TheaterRepoImpl;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepo;
import com.joysistvi.cinebookapp.repository.UsersRegistrationRepoImpl;
import com.joysistvi.cinebookapp.service.BookingServiceImpl;
import com.joysistvi.cinebookapp.service.BookingSeatsServiceImpl;
import com.joysistvi.cinebookapp.service.MoviesServiceImpl;
import com.joysistvi.cinebookapp.service.SeatMapServiceImpl;
import com.joysistvi.cinebookapp.service.ShowtimeServiceImpl;
import com.joysistvi.cinebookapp.service.TheaterServiceImpl;
import com.joysistvi.cinebookapp.service.UserBookingServiceImpl;
import com.joysistvi.cinebookapp.service.UsersRegistrationService;
import com.joysistvi.cinebookapp.service.UsersRegistrationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        DatabaseBootstrap bootstrap = new DatabaseBootstrap(); // Create database upon running the application
        DatabaseMigration migration = new DatabaseMigration(); // Create tables upon running the application
        DatabaseConnection databaseConnection = new DatabaseConnection();
        AdminAccountInitializer adminAccountInitializer = new AdminAccountInitializer(); // Create admin account upon
                                                                                         // running the application

        try {
            bootstrap.createDatabaseIfNotExists();
            migration.migrate();
            new CinemaDataSeeder(databaseConnection).run();
            if (args.length > 0 && "--seed-cinema-only".equals(args[0])) {
                System.out.println("Cinema seed completed. No users, bookings, or payments were seeded.");
                return;
            }
            databaseConnection.testConnection();
            adminAccountInitializer.run();
        } catch (RuntimeException e) {
            logger.error("Application startup encountered an error", e);
            System.out.println("[!] Startup warning: some features may not work. Check the logs for details.");
        }

        Scanner scanner = new Scanner(System.in);
        UsersController usersController = new UsersController();
        LoginView loginView = new LoginView(usersController, scanner);

        UsersRegistrationRepo usersRegistrationRepo = new UsersRegistrationRepoImpl(databaseConnection);
        UsersRegistrationService usersRegistrationService = new UsersRegistrationServiceImpl(usersRegistrationRepo);
        UsersRegistrationController usersRegistrationController = new UsersRegistrationController(
                usersRegistrationService);
        UsersRegistraionView usersRegistraionView = new UsersRegistraionView(usersRegistrationController, scanner);

        MoviesController moviesController = new MoviesController(
                new MoviesServiceImpl(new MoviesRepoImpl()));
        ShowtimeController showtimeController = new ShowtimeController(
                new ShowtimeServiceImpl(new ShowtimeRepoImpl()));
        UserBookingController userBookingController = new UserBookingController(new UserBookingServiceImpl());
        BookingController bookingController = new BookingController(
                new BookingServiceImpl(new BookingRepoImpl()));
        BookingSeatsController bookingSeatsController = new BookingSeatsController(
                new BookingSeatsServiceImpl(new BookingSeatsRepoImpl(databaseConnection)));
        PaymentController paymentController = new PaymentController();
        TheaterController theaterController = new TheaterController(
                new TheaterServiceImpl(new TheaterRepoImpl(databaseConnection)));
        SeatMapController seatMapController = new SeatMapController(
                new SeatMapServiceImpl(new SeatMapRepoImpl(databaseConnection)));

        CinemaCliView cliView = new CinemaCliView(scanner, loginView, usersRegistraionView, moviesController,
                showtimeController, userBookingController, bookingController, bookingSeatsController,
                paymentController, theaterController,
                seatMapController, usersController, usersRegistrationController);
        cliView.run();
    }
}
