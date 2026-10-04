package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.SeatMapController;
import com.joysistvi.cinebookapp.controller.TheaterController;
import com.joysistvi.cinebookapp.model.Seat;
import com.joysistvi.cinebookapp.model.Theater;

import java.util.List;
import java.util.Scanner;

public class SeatMapView {

    private final SeatMapController seatMapController;
    private final TheaterController theaterController;
    private final Scanner scanner;

    public SeatMapView(SeatMapController seatMapController, TheaterController theaterController, Scanner scanner) {
        this.seatMapController = seatMapController;
        this.theaterController = theaterController;
        this.scanner = scanner;
    }

    public void run() {
        boolean active = true;
        while (active) {
            Header.print();
            CliLayout.println("[ Admin Portal | Theater Seat Maps ]");
            CliLayout.println("-".repeat(88));
            List<Theater> theaters = theaterController.handleViewAllTheater();
            CliLayout.table(List.of("ID", "Theater", "Location", "Status"), theaters.stream()
                    .map(theater -> List.of(theater.getId(), theater.getName(), theater.getLocation(),
                            theater.getStatus()))
                    .toList());
            String selection = prompt("Enter Theater ID to manage seats (B to go back): ");
            if (selection.equalsIgnoreCase("B")) {
                return;
            }
            int theaterId = parseInt(selection);
            Theater theater = theaterController.handleReadTheaterById(theaterId);
            if (theater == null) {
                pause("Theater not found. Press [ENTER] to continue...");
                continue;
            }
            manageTheaterSeats(theater);
        }
    }

    private void manageTheaterSeats(Theater theater) {
        boolean active = true;
        while (active) {
            Header.print();
            CliLayout.println("[ Admin Portal | Seat Map | " + theater.getName() + " ]");
            CliLayout.println("-".repeat(88));
            List<Seat> seats = seatMapController.getSeats(theater.getId());
            if (seats.isEmpty()) {
                CliLayout.println("No seats are configured for this theater.");
            } else {
                CliLayout.table(List.of("Seat ID", "Seat Code", "Row", "Number"), seats.stream()
                        .map(seat -> List.of(seat.getId(), seat.getSeatCode(), seat.getSeatRow(), seat.getSeatNumber()))
                        .toList());
            }
            CliLayout.println("\n[A] Add Seat | [D] Remove Seat | [B] Back");
            switch (prompt("Select an action: ").toUpperCase()) {
                case "A" -> addSeat(theater);
                case "D" -> removeSeat(theater, seats);
                case "B" -> active = false;
                default -> pause("Invalid action. Press [ENTER] to continue...");
            }
        }
    }

    private void addSeat(Theater theater) {
        String row = prompt("Seat row (for example A): ").toUpperCase();
        int number = parseInt(prompt("Seat number: "));
        boolean added = seatMapController.addSeat(theater.getId(), row, number);
        pause(added ? "Seat added to the map. Press [ENTER] to continue..."
                : "Seat details are invalid or already exist. Press [ENTER] to continue...");
    }

    private void removeSeat(Theater theater, List<Seat> seats) {
        if (seats.isEmpty()) {
            pause("There are no seats to remove. Press [ENTER] to continue...");
            return;
        }
        int seatId = parseInt(prompt("Seat ID to remove: "));
        if (seats.stream().noneMatch(seat -> seat.getId() == seatId)) {
            pause("Seat not found in this theater. Press [ENTER] to continue...");
            return;
        }
        if (!"Y".equalsIgnoreCase(prompt("Remove seat " + seatId + "? (Y/N): "))) {
            return;
        }
        boolean removed = seatMapController.removeSeat(seatId);
        pause(removed ? "Seat removed. Press [ENTER] to continue..."
                : "Seat could not be removed; it may have bookings. Press [ENTER] to continue...");
    }

    private String prompt(String label) {
        CliLayout.print(label);
        return scanner.nextLine().trim();
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void pause(String message) {
        CliLayout.print(message);
        scanner.nextLine();
    }
}
