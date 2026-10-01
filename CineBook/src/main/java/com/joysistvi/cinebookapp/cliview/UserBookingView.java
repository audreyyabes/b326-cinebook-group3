package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.UserBookingController;

import java.util.List;
import java.util.Scanner;

public class UserBookingView {

    private final UserBookingController userBookingController;
    private final Scanner scanner;

    public UserBookingView(UserBookingController userBookingController) {
        this.userBookingController = userBookingController;
        this.scanner = new Scanner(System.in);
    }

    public void display(int userId) {

        displayHeader();

        List<String> bookings = userBookingController.getMyBookings(userId);

        displayBookings(bookings);

        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        System.out.println("[B] Back to User Portal");
        System.out.print("Select Option: ");

        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("B")) {
            return;
        }
    }

    private void displayHeader() {

        System.out.println("=====================================================================================================================");
        System.out.println("                      ____ _____ _  _ _____ ____  ____  ____  _  _ ");
        System.out.println("                     / ___|_   _| || | ____| __ )/ ___|/ ___|| || |");
        System.out.println("                     | |     | | | || |  _| |  _ \\ |  /| |   | || |");
        System.out.println("                     | |___  | | | || | |___| |_) | |__| |___| __ |");
        System.out.println("                     \\____| |_| |_||_|_____|____/\\____|\\____|_||_|");
        System.out.println("                                  THEATRE CLI v1.0");
        System.out.println("=====================================================================================================================");
        System.out.println("[ User Portal | My Bookings ]");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }

    private void displayBookings(List<String> bookings) {

        System.out.println();
        System.out.println("Booking History");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");

        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        int codeWidth = 18;
        int movieWidth = 25;
        int theaterWidth = 15;
        int dateWidth = 20;
        int totalWidth = 12;
        int statusWidth = 12;

        System.out.printf(
                "%-" + codeWidth + "s | %-" + movieWidth + "s | %-" + theaterWidth + "s | %-" +
                        dateWidth + "s | %-" + totalWidth + "s | %-" + statusWidth + "s%n",
                "Code",
                "Movie",
                "Theater",
                "Date/Time",
                "Total",
                "Status"
        );

        System.out.println(
                "-".repeat(codeWidth) + "-+-" +
                        "-".repeat(movieWidth) + "-+-" +
                        "-".repeat(theaterWidth) + "-+-" +
                        "-".repeat(dateWidth) + "-+-" +
                        "-".repeat(totalWidth) + "-+-" +
                        "-".repeat(statusWidth)
        );

        for (String booking : bookings) {

            String[] parts = booking.split("\\|");

            if (parts.length >= 6) {

                String code = parts[0].trim();
                String movie = parts[1].trim();
                String dateTime = parts[2].trim();
                String theater = parts[3].trim();
                String total = parts[4].trim();
                String status = parts[5].trim();

                System.out.printf(
                        "%-" + codeWidth + "s | %-" + movieWidth + "s | %-" +
                                theaterWidth + "s | %-" + dateWidth + "s | %" +
                                totalWidth + "s | %-" + statusWidth + "s%n",
                        code,
                        movie,
                        theater,
                        dateTime,
                        total,
                        status
                );
            }
        }

        System.out.println(
                "-".repeat(codeWidth) + "-+-" +
                        "-".repeat(movieWidth) + "-+-" +
                        "-".repeat(theaterWidth) + "-+-" +
                        "-".repeat(dateWidth) + "-+-" +
                        "-".repeat(totalWidth) + "-+-" +
                        "-".repeat(statusWidth)
        );
    }
}
