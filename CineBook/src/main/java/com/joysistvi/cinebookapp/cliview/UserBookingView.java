package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.UserBookingController;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class UserBookingView {

    private final UserBookingController userBookingController;
    private final Scanner scanner;

    public UserBookingView(UserBookingController userBookingController) {
        this(userBookingController, new Scanner(System.in));
    }

    public UserBookingView(UserBookingController userBookingController, Scanner scanner) {
        this.userBookingController = userBookingController;
        this.scanner = scanner;
    }

    public void display(int userId) {
        List<String> bookings = userBookingController.getMyBookings(userId);
        while (true) {
            displayHeader();
            displayBookings(bookings);
            CliLayout.println();
            CliLayout.println("-".repeat(88));
            CliLayout.print("Enter booking ID to view receipt, or B to return: ");
            String option = scanner.nextLine().trim();
            if (option.equalsIgnoreCase("B")) return;
            try {
                Integer.parseInt(option);
                String[] booking = bookings.stream().map(value -> value.split("\\s*\\|\\s*"))
                        .filter(parts -> parts.length >= 11 && parts[0].equals(option))
                        .findFirst().orElse(null);
                if (booking == null) {
                    pause("Booking not found. Press [ENTER] to continue...");
                    continue;
                }
                displayReceipt(booking);
            } catch (NumberFormatException e) {
                pause("Enter a numeric booking ID or B. Press [ENTER] to continue...");
            }
        }
    }

    private void displayHeader() {

        Header.print();
        CliLayout.println("[ User Portal | My Bookings ]");
        CliLayout.println("-".repeat(88));
    }

    private void displayBookings(List<String> bookings) {

        CliLayout.println();
        CliLayout.println("Booking History");
        CliLayout.println("-".repeat(88));

        if (bookings == null || bookings.isEmpty()) {
            CliLayout.println("No bookings found.");
            return;
        }

        List<List<?>> rows = new ArrayList<>();
        for (String booking : bookings) {
            String[] parts = booking.split("\\|");
            if (parts.length >= 11) {
                rows.add(List.of(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                        parts[4].trim(), parts[5].trim(), parts[6].trim(), parts[7].trim(), parts[8].trim(),
                        parts[9].trim(), parts[10].trim()));
            }
        }
        CliLayout.table(List.of("ID", "Code", "Movie", "Theater", "Date/Time", "Total", "Booking Status",
                "Seats", "Payment Method", "Reference", "Payment Status"), rows);
    }

    private void displayReceipt(String[] booking) {
        Header.print();
        CliLayout.println("[ Customer Portal | Payment Receipt ]");
        CliLayout.println("-".repeat(88));
        CliLayout.println("Booking Code    : " + booking[1]);
        CliLayout.println("Movie           : " + booking[2]);
        CliLayout.println("Theater         : " + booking[4]);
        CliLayout.println("Showtime        : " + booking[3]);
        CliLayout.println("Seats           : " + (booking[7].isBlank() ? "-" : booking[7]));
        CliLayout.println("Total           : ₱" + booking[5]);
        CliLayout.println("Booking Status  : " + booking[6]);
        CliLayout.println("Payment Method  : " + (booking[8].isBlank() ? "-" : booking[8]));
        CliLayout.println("Payment Ref.    : " + (booking[9].isBlank() ? "-" : booking[9]));
        CliLayout.println("Payment Status  : " + (booking[10].isBlank() ? "-" : booking[10]));
        pause("\nPress [ENTER] to return to Booking History...");
    }

    private void pause(String message) {
        CliLayout.print(message);
        scanner.nextLine();
    }
}
