package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.BookingSeatsController;
import com.joysistvi.cinebookapp.model.BookingSeats;

import java.util.List;
import java.util.Scanner;

public class BookingSeatsView {

    private final BookingSeatsController bookingSeatsController;
    private final Scanner scanner;

    public BookingSeatsView(BookingSeatsController bookingSeatsController, Scanner scanner) {
        this.bookingSeatsController = bookingSeatsController;
        this.scanner = scanner;
    }

    public void runBookingSeats() {
        int choice;

        do {
            CliLayout.println("\n===== BOOKING SEATS MENU =====");
            CliLayout.println("[1] View All Booking Seats");
            CliLayout.println("[2] View Booking Seat by ID");
            CliLayout.println("[3] Update Booking Seat Price");
            CliLayout.println("[4] Delete Booking Seat");
            CliLayout.println("[0] Back");
            CliLayout.print("Enter choice: ");

            choice = inputInt();

            switch (choice) {
                case 1:
                    viewAllBookingSeats();
                    break;
                case 2:
                    viewBookingSeatById();
                    break;
                case 3:
                    updateBookingSeatPrice();
                    break;
                case 4:
                    deleteBookingSeat();
                    break;
                case 0:
                    CliLayout.println("Returning...");
                    break;
                default:
                    CliLayout.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    private void viewAllBookingSeats() {
        List<BookingSeats> bookingSeatsList = bookingSeatsController.handleAllBookingSeats();
        if (bookingSeatsList == null || bookingSeatsList.isEmpty()) {
            CliLayout.println("No booking seats found.");
            return;
        }

        CliLayout.println("\nBOOKING SEATS");
        CliLayout.table(List.of("ID", "Booking ID", "Seat ID", "Price"), bookingSeatsList.stream()
                .map(bookingSeats -> List.of(bookingSeats.getId(), bookingSeats.getBookingId(),
                        bookingSeats.getSeat_id(), String.format("₱%.2f", bookingSeats.getPrice())))
                .toList());

    }

    private void viewBookingSeatById() {
        CliLayout.print("Enter Booking Seat ID: ");
        int id = inputInt();

        BookingSeats bookingSeats = bookingSeatsController.handleReadBookingSeatsById(id);

        if (bookingSeats == null) {
            CliLayout.println("Booking seat not found.");
            return;
        }

        displayBookingSeat(bookingSeats);
    }

    private void updateBookingSeatPrice() {
        CliLayout.print("Enter Booking Seat ID to update: ");
        int id = inputInt();

        BookingSeats bookingSeats = bookingSeatsController.handleReadBookingSeatsById(id);

        if (bookingSeats == null) {
            CliLayout.println("Booking seat not found.");
            return;
        }

        CliLayout.print("Enter new price: ");
        double price = inputDouble();

        bookingSeats.setPrice(price);

        boolean updated = bookingSeatsController.handleUpdateBookingSeats(bookingSeats);

        if (updated) {
            CliLayout.println("Booking seat price updated successfully.");
        } else {
            CliLayout.println("Failed to update booking seat price.");
        }
    }

    private void deleteBookingSeat() {
        CliLayout.print("Enter Booking Seat ID to delete: ");
        int id = inputInt();

        BookingSeats bookingSeats = bookingSeatsController.handleReadBookingSeatsById(id);

        if (bookingSeats == null) {
            CliLayout.println("Booking seat not found.");
            return;
        }

        boolean deleted = bookingSeatsController.handleBookingSeats(id);

        if (deleted) {
            CliLayout.println("Booking seat deleted successfully.");
        } else {
            CliLayout.println("Failed to delete booking seat.");
        }
    }

    private void displayBookingSeat(BookingSeats bookingSeats) {
        CliLayout.table(List.of("ID", "Booking ID", "Seat ID", "Price"), List.of(List.of(
                bookingSeats.getId(), bookingSeats.getBookingId(), bookingSeats.getSeat_id(),
                String.format("₱%.2f", bookingSeats.getPrice()))));
    }

    private int inputInt() {
        while (!scanner.hasNextInt()) {
            CliLayout.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double inputDouble() {
        while (!scanner.hasNextDouble()) {
            CliLayout.print("Invalid input. Enter a valid price: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}