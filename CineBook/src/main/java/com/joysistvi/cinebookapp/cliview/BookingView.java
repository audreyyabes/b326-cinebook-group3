package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.model.BookingAudit;

import java.util.List;
import java.util.Scanner;

public class BookingView {

    private static final int WIDTH = 88;

    private final BookingController bookingController;
    private final Scanner scanner;

    public BookingView(BookingController bookingController) {
        this(bookingController, new Scanner(System.in));
    }

    public BookingView(BookingController bookingController, Scanner scanner) {
        this.bookingController = bookingController;
        this.scanner = scanner;
    }

    public void showBookingAudit() {

        while (true) {

            displayHeader();

            displayBookings();

            displayFinancialMetrics();

            CliLayout.println("-".repeat(WIDTH));
            CliLayout.println("[P] Process / Confirm Pending Payment | [B] Back to Admin Portal");

            CliLayout.print("Select Option: ");

            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("P")) {

                processPendingPayment();

            } else if (option.equalsIgnoreCase("B")) {

                CliLayout.println("Returning to Admin Portal...");

                break;

            } else {

                CliLayout.println("Invalid option.");

                pause();
            }
        }
    }

    public void processPendingPayments() {
        while (true) {
            displayHeader();
            List<BookingAudit> pending = bookingController.getBookingAudit().stream()
                    .filter(booking -> "pending".equalsIgnoreCase(booking.getBookingStatus()))
                    .toList();
            if (pending.isEmpty()) {
                pauseWithMessage("There are no pending payments. Press [ENTER] to return...");
                return;
            }
                CliLayout.table(List.of("ID", "Booking Code", "Customer", "Total", "Payment"),
                    pending.stream().map(booking -> List.of(booking.getId(), booking.getBookingCode(),
                        booking.getCustomerName(), String.format("₱%,.2f", booking.getTotalAmount()),
                        booking.getPaymentDetails()))
                        .toList());

            String selection = prompt("Enter booking ID to confirm, or B to return: ");
            if (selection.equalsIgnoreCase("B")) {
                return;
            }
            int bookingId;
            try {
                bookingId = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                pauseWithMessage("Enter a valid booking ID. Press [ENTER] to continue...");
                continue;
            }
            if (pending.stream().noneMatch(booking -> booking.getId() == bookingId)) {
                pauseWithMessage("That booking is not pending. Press [ENTER] to continue...");
                continue;
            }
            if ("Y".equalsIgnoreCase(prompt("Confirm payment for booking " + bookingId + "? (Y/N): "))) {
                boolean confirmed = bookingController.confirmBooking(bookingId);
                pauseWithMessage(confirmed ? "Payment confirmed. Press [ENTER] to continue..."
                        : "Payment could not be confirmed. Press [ENTER] to continue...");
            }
        }
    }

    private void displayHeader() {
        Header.print();
        CliLayout.println("[ Admin Portal | Revenue & Transaction Audit ]");
        CliLayout.println("-".repeat(WIDTH));
    }

    private void displayBookings() {

        List<BookingAudit> bookings =
                bookingController.getBookingAudit();

        if (bookings.isEmpty()) {
            CliLayout.println("No bookings found.");
            CliLayout.println();
            return;
        }

        CliLayout.table(List.of("ID", "Code", "Customer", "Total", "Booking Status", "Payment Details"),
            bookings.stream().map(booking -> List.of(booking.getId(), booking.getBookingCode(),
                booking.getCustomerName(), String.format("₱%,.2f", booking.getTotalAmount()),
                booking.getBookingStatus(), booking.getPaymentDetails()))
                .toList());
    }

    private void displayFinancialMetrics() {

        double confirmedRevenue = bookingController.getConfirmedRevenue();

        double pendingTotal = bookingController.getPendingTotal();

        int totalTickets = bookingController.getTotalTicketsReserved();

        CliLayout.println("-".repeat(WIDTH));

        CliLayout.println("FINANCIAL METRICS:");

        CliLayout.printf(
                "Total Confirmed Revenue : ₱%,.2f%n",
                confirmedRevenue
        );

        CliLayout.printf(
                "Pending Unpaid Total    : ₱%,.2f%n",
                pendingTotal
        );

        CliLayout.println(
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

            CliLayout.println();
            CliLayout.println(
                    "There are no pending payments."
            );

            pause();

            return;
        }

        CliLayout.println();
        CliLayout.println("----- PROCESS PENDING PAYMENT -----");

        CliLayout.println("Booking ID   : " + pendingBooking.getId());

        CliLayout.println("Booking Code : " + pendingBooking.getBookingCode());

        CliLayout.println("Customer     : "
                + pendingBooking.getCustomerName());

        CliLayout.printf("Amount       : ₱%,.2f%n", pendingBooking.getTotalAmount());

        CliLayout.println("Payment      : " + pendingBooking.getPaymentDetails());

        CliLayout.println();

        CliLayout.print("Confirm payment? (Y/N): ");

        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")) {

            boolean success = bookingController.confirmBooking(pendingBooking.getId());

            if (success) {

                CliLayout.println();
                CliLayout.println("Payment confirmed successfully!");

            } else {

                CliLayout.println();
                CliLayout.println("Failed to confirm payment.");
            }

        } else {

            CliLayout.println("Payment was not confirmed.");
        }

        pause();
    }

    private void pause() {

        CliLayout.println();
        CliLayout.print("Press ENTER to continue...");

        scanner.nextLine();
    }

    private String prompt(String label) {
        CliLayout.print(label);
        return scanner.nextLine().trim();
    }

    private void pauseWithMessage(String message) {
        CliLayout.print(message);
        scanner.nextLine();
    }
}
