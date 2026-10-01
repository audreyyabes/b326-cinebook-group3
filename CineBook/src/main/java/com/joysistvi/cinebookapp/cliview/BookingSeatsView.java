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
            System.out.println("\n===== BOOKING SEATS MENU =====");
            System.out.println("[1] View All Booking Seats");
            System.out.println("[2] View Booking Seat by ID");
            System.out.println("[3] Update Booking Seat Price");
            System.out.println("[4] Delete Booking Seat");
            System.out.println("[0] Back");
            System.out.print("Enter choice: ");

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
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    private void viewAllBookingSeats() {
        List<BookingSeats> bookingSeatsList = bookingSeatsController.handleAllBookingSeats();
        if (bookingSeatsList == null || bookingSeatsList.isEmpty()) {
            System.out.println("No booking seats found.");
            return;
        }

        System.out.println("\n================ BOOKING SEATS ================");
        System.out.printf("%-5s | %-12s | %-8s | %-10s%n", "ID", "Booking ID", "Seat ID", "Price");
        System.out.println("------------------------------------------------");

        for (BookingSeats bookingSeats : bookingSeatsList) {
            displayBookingSeat(bookingSeats);
        }


    }

    private void viewBookingSeatById() {
        System.out.print("Enter Booking Seat ID: ");
        int id = inputInt();

        BookingSeats bookingSeats = bookingSeatsController.handleReadBookingSeatsById(id);

        if (bookingSeats == null) {
            System.out.println("Booking seat not found.");
            return;
        }

        displayBookingSeat(bookingSeats);
    }

    private void updateBookingSeatPrice() {
        System.out.print("Enter Booking Seat ID to update: ");
        int id = inputInt();

        BookingSeats bookingSeats = bookingSeatsController.handleReadBookingSeatsById(id);

        if (bookingSeats == null) {
            System.out.println("Booking seat not found.");
            return;
        }

        System.out.print("Enter new price: ");
        double price = inputDouble();

        bookingSeats.setPrice(price);

        boolean updated = bookingSeatsController.handleUpdateBookingSeats(bookingSeats);

        if (updated) {
            System.out.println("Booking seat price updated successfully.");
        } else {
            System.out.println("Failed to update booking seat price.");
        }
    }

    private void deleteBookingSeat() {
        System.out.print("Enter Booking Seat ID to delete: ");
        int id = inputInt();

        BookingSeats bookingSeats = bookingSeatsController.handleReadBookingSeatsById(id);

        if (bookingSeats == null) {
            System.out.println("Booking seat not found.");
            return;
        }

        boolean deleted = bookingSeatsController.handleBookingSeats(id);

        if (deleted) {
            System.out.println("Booking seat deleted successfully.");
        } else {
            System.out.println("Failed to delete booking seat.");
        }
    }

    private void displayBookingSeat(BookingSeats bookingSeats) {
        System.out.printf(
                "%-5d | %-12d | %-8d | %-10.2f%n",
                bookingSeats.getId(),
                bookingSeats.getBookingId(),
                bookingSeats.getSeat_id(),
                bookingSeats.getPrice()
        );
    }

    private int inputInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double inputDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter a valid price: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}