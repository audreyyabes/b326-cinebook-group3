package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.controller.BookingSeatsController;
import com.joysistvi.cinebookapp.controller.PaymentController;
import com.joysistvi.cinebookapp.model.Booking;
import com.joysistvi.cinebookapp.model.BookingSeats;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PaymentView {

    private static final int WIDTH = 88;
    private static final String DIVIDER = "-".repeat(WIDTH);
    private static final DateTimeFormatter BOOKING_CODE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final BookingController bookingController;
    private final BookingSeatsController bookingSeatsController;
    private final PaymentController paymentController;
    private final Scanner scanner;

    public PaymentView(BookingController bookingController,
                        BookingSeatsController bookingSeatsController,
                        PaymentController paymentController,
                        Scanner scanner) {
        this.bookingController = bookingController;
        this.bookingSeatsController = bookingSeatsController;
        this.paymentController = paymentController;
        this.scanner = scanner;
    }

    public void show(String customerName, int userId, int showtimeId, String movieTitle, String theaterLabel,
                      String showtimeLabel, List<String> seatCodes, List<Integer> seatIds, double ticketPrice) {

        double totalAmount = ticketPrice * seatIds.size();

        Header.print();
        System.out.println("[ User: " + customerName + " | Booking Confirmation ]");
        System.out.println(DIVIDER);
        System.out.println();
        System.out.println(center(">>> BOOKING SUMMARY <<<"));
        System.out.println();
        System.out.println(" Movie          : " + movieTitle);
        System.out.println(" Theater        : " + theaterLabel);
        System.out.println(" Showtime       : " + showtimeLabel);
        System.out.println(" Seats Selected : " + String.join(", ", seatCodes) + " (" + seatIds.size() + " seats)");
        System.out.printf(" Rate / Seat    : ₱ %.2f x %d%n", ticketPrice, seatIds.size());
        System.out.println(DIVIDER);
        System.out.printf(" TOTAL AMOUNT   : ₱ %.2f%n", totalAmount);
        System.out.println(DIVIDER);
        System.out.println();
        System.out.println(" Select Payment Method:");
        System.out.println("  [1] GCash");
        System.out.println("  [2] Credit Card");
        System.out.println("  [3] Cash at Counter (Status: Pending)");
        System.out.println();
        System.out.print("Choose option [1-3]: ");
        String choice = scanner.nextLine().trim();

        String paymentMethod = resolvePaymentMethod(choice);
        if (paymentMethod == null) {
            System.out.println();
            System.out.println("[X] Invalid option. Returning to Customer Menu...");
            pause();
            return;
        }

        String paymentReference = null;
        if (choice.equals("1")) {
            System.out.print("Enter GCash Reference Number: ");
            paymentReference = scanner.nextLine().trim();
        }

        System.out.println();
        System.out.print("Creating records in `bookings`, `booking_seats`, & `payments`... ");

        boolean paidNow = choice.equals("1") || choice.equals("2");
        String bookingStatus = paidNow ? "confirmed" : "pending";
        String bookingCode = generateBookingCode();

        Booking booking = new Booking(0, bookingCode, userId, showtimeId, LocalDateTime.now(),
                totalAmount, bookingStatus);

        if (!bookingController.createBooking(booking)) {
            System.out.println("FAILED!");
            System.out.println();
            System.out.println("[X] Could not create the booking. Please try again.");
            pause();
            return;
        }

        Booking savedBooking = findBookingByCode(bookingCode);
        if (savedBooking == null) {
            System.out.println("FAILED!");
            System.out.println();
            System.out.println("[X] Booking was created but could not be found afterward.");
            pause();
            return;
        }

        for (Integer seatId : seatIds) {
            BookingSeats bookingSeat = new BookingSeats(0, savedBooking.getId(), seatId, ticketPrice);
            bookingSeatsController.handleCreateBookingSeats(bookingSeat);
        }

        paymentController.processPayment(savedBooking.getId(), BigDecimal.valueOf(totalAmount),
                paymentMethod, paymentReference);

        System.out.println("SUCCESS!");
        System.out.println("Booking Code: " + savedBooking.getBookingCode()
                + " | Status: " + savedBooking.getStatus().toUpperCase());
        System.out.println();
        System.out.print("Press [ENTER] to return to Customer Menu...");
        scanner.nextLine();
    }

    private String resolvePaymentMethod(String choice) {
        return switch (choice) {
            case "1" -> "GCash";
            case "2" -> "Credit Card";
            case "3" -> "Cash at Counter";
            default -> null;
        };
    }

    // The booking code is a business key we generate ourselves, like CB-20261001-003,
    // so we re-fetch the booking by that code to get the id the database assigned to it.
    private Booking findBookingByCode(String bookingCode) {
        List<Booking> bookings = bookingController.getAllBookings();
        for (Booking booking : bookings) {
            if (bookingCode.equals(booking.getBookingCode())) {
                return booking;
            }
        }
        return null;
    }

    // Builds a code like CB-20261001-003: "CB" + today's date + a daily sequence number.
    private String generateBookingCode() {
        String todayPrefix = "CB-" + LocalDate.now().format(BOOKING_CODE_DATE_FORMAT) + "-";

        int sequence = 1;
        for (Booking booking : bookingController.getAllBookings()) {
            if (booking.getBookingCode() != null && booking.getBookingCode().startsWith(todayPrefix)) {
                sequence++;
            }
        }

        return todayPrefix + String.format("%03d", sequence);
    }

    private void pause() {
        System.out.print("Press [ENTER] to continue...");
        scanner.nextLine();
    }

    private String center(String text) {
        int padding = Math.max((WIDTH - text.length()) / 2, 0);
        return " ".repeat(padding) + text;
    }
}
