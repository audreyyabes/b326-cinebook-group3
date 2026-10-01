package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.SeatController;
import com.joysistvi.cinebookapp.model.Seat;

import java.util.List;
import java.util.Scanner;

public class SeatView {

    private final SeatController seatController;
    private final Scanner scanner;

    public SeatView(SeatController seatController, Scanner scanner) {
        this.seatController = seatController;
        this.scanner = scanner;
    }

    public void show(int theaterId, String theaterName, String movieTitle, double ticketPrice) {

        List<Seat> seats = seatController.getSeatsByTheaterId(theaterId);

        System.out.println();
        System.out.println("========================================================================================");
        System.out.println("                      ____ _____ _  _ _____ ____  ____  ____  _  _ ");
        System.out.println("                     / ___|_   _| || | ____| __ )/ ___|/ ___|| || |");
        System.out.println("                    | |     | | | || |  _| |  _ \\ |  /| |   | || |");
        System.out.println("                    | |___  | | | || | |___| |_) | |__| |___| __ |");
        System.out.println("                     \\____| |_| |_||_|_____|____/\\____|\\____|_||_|");
        System.out.println("                                  THEATRE CLI v1.0");
        System.out.println("========================================================================================");

        System.out.printf(
                "[ Theater: %s | Movie: %s | Price/Seat: ₱%.2f ]%n",
                theaterName,
                movieTitle,
                ticketPrice
        );

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println("                       +----------------------------------+");
        System.out.println("                       |           S C R E E N            |");
        System.out.println("                       +----------------------------------+");
        System.out.println();

        if (seats.isEmpty()) {
            System.out.println("                         No seats found for this theater.");
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------");
            return;
        }

        printSeatLayout(seats);

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Legend: [ A1 ] Available  |  [ X ] Reserved/Booked");
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.print("Enter seat codes separated by commas (e.g. A3, A4) or 'B' to cancel: ");

        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("B")) {
            return;
        }

        String[] selectedSeats = input.split(",");

        System.out.println();

        for (String seatCode : selectedSeats) {

            String code = seatCode.trim();

            if (code.isEmpty()) {
                continue;
            }

            boolean found = false;

            for (Seat seat : seats) {

                if (seat.getSeatCode().equalsIgnoreCase(code)) {
                    found = true;

                    System.out.println("Selected seat: " + seat.getSeatCode());
                    break;
                }
            }

            if (!found) {
                System.out.println("Seat not found: " + code);
            }
        }
    }

    private void printSeatLayout(List<Seat> seats) {

        String currentRow = "";

        for (Seat seat : seats) {

            String row = seat.getSeatRow();

            if (!row.equals(currentRow)) {

                if (!currentRow.isEmpty()) {
                    System.out.println();
                }

                System.out.printf("%-18s", "Row " + row + ":");

                currentRow = row;
            }

            System.out.printf("[%-3s] ", seat.getSeatCode());
        }

        System.out.println();
    }
}