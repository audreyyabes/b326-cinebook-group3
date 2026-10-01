package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.model.BookingAudit;

import java.util.List;
import java.util.Scanner;

public class BookingView {

    private final BookingController bookingController;
    private final Scanner scanner;

    public BookingView(BookingController bookingController) {
        this.bookingController = bookingController;
        this.scanner = new Scanner(System.in);
    }

    public void showBookingAudit() {

        while (true) {

            displayHeader();

            displayBookings();

            displayFinancialMetrics();

            System.out.println(
                    "---------------------------------------------------------------------------------------------"
            );

            System.out.println("[P] Process / Confirm Pending Payment  |  [B] Back to Admin Portal");

            System.out.print("Select Option: ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("P")) {

                processPendingPayment();

            } else if (option.equalsIgnoreCase("B")) {

                System.out.println("Returning to Admin Portal...");

                break;

            } else {

                System.out.println("Invalid option.");

                pause();
            }
        }
    }

    private void displayHeader() {

        System.out.println();
        System.out.println("=============================================================================================");

        System.out.println(
                "                      ____ _____ _  _ _____ ____  ____  ____  _  _ "
        );

        System.out.println(
                "                     / ___|_   _| || | ____| __ )/ ___|/ ___|| || |"
        );

        System.out.println(
                "                     | |     | | | || |  _| |  _ \\ |  /| |   | || |"
        );

        System.out.println(
                "                     | |___  | | | || | |___| |_) | |__| |___| __ |"
        );

        System.out.println(
                "                      \\____| |_| |_||_|_____|____/\\____|\\____|_||_|"
        );

        System.out.println(
                "                                  THEATRE CLI v1.0"
        );

        System.out.println("=============================================================================================");

        System.out.println(
                "[ Admin Portal | Revenue & Transaction Audit ]"
        );

        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void displayBookings() {

        List<BookingAudit> bookings =
                bookingController.getBookingAudit();

        System.out.printf(
                "%-4s | %-17s | %-15s | %-10s | %-14s | %-20s%n",
                "ID",
                "Code",
                "Customer",
                "Total",
                "Booking Status",
                "Payment Details"
        );

        System.out.println("----+-----------------+---------------+----------+----------------+--------------------------");

        for (BookingAudit booking : bookings) {

            System.out.printf(
                    "%-4d | %-17s | %-15s | ₱%8.2f | %-14s | %-20s%n",

                    booking.getId(),

                    booking.getBookingCode(),

                    booking.getCustomerName(),

                    booking.getTotalAmount(),

                    booking.getBookingStatus(),

                    booking.getPaymentDetails()
            );
        }

        System.out.println();
    }

    private void displayFinancialMetrics() {

        double confirmedRevenue = bookingController.getConfirmedRevenue();

        double pendingTotal = bookingController.getPendingTotal();

        int totalTickets = bookingController.getTotalTicketsReserved();

        System.out.println("---------------------------------------------------------------------------------------------");

        System.out.println("FINANCIAL METRICS:");

        System.out.printf(
                "Total Confirmed Revenue : ₱%,.2f%n",
                confirmedRevenue
        );

        System.out.printf(
                "Pending Unpaid Total    : ₱%,.2f%n",
                pendingTotal
        );

        System.out.println(
                "Total Tickets Reserved  : "
                        + totalTickets
                        + " seats"
        );
    }

    private void processPendingPayment() {

        List<BookingAudit> bookings =
                bookingController.getBookingAudit();

        BookingAudit pendingBooking = null;

        for (BookingAudit booking : bookings) {

            if ("pending".equalsIgnoreCase(
                    booking.getBookingStatus())) {

                pendingBooking = booking;
                break;
            }
        }

        if (pendingBooking == null) {

            System.out.println();
            System.out.println(
                    "There are no pending payments."
            );

            pause();

            return;
        }

        System.out.println();
        System.out.println("----- PROCESS PENDING PAYMENT -----");

        System.out.println("Booking ID   : " + pendingBooking.getId());

        System.out.println("Booking Code : " + pendingBooking.getBookingCode());

        System.out.println("Customer     : "
                + pendingBooking.getCustomerName());

        System.out.printf("Amount       : ₱%,.2f%n", pendingBooking.getTotalAmount());

        System.out.println("Payment      : " + pendingBooking.getPaymentDetails());

        System.out.println();

        System.out.print("Confirm payment? (Y/N): ");

        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")) {

            boolean success = bookingController.confirmBooking(pendingBooking.getId());

            if (success) {

                System.out.println();
                System.out.println("Payment confirmed successfully!");

            } else {

                System.out.println();
                System.out.println("Failed to confirm payment.");
            }

        } else {

            System.out.println("Payment was not confirmed.");
        }

        pause();
    }

    private void pause() {

        System.out.println();
        System.out.print("Press ENTER to continue...");

        scanner.nextLine();
    }
}
