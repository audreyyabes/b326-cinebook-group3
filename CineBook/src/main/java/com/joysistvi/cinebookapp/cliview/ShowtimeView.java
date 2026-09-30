package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.ShowtimeController;
import com.joysistvi.cinebookapp.model.Showtime;

import java.util.List;
import java.util.Scanner;

public class ShowtimeView {

    private final ShowtimeController showtimeController;
    private final Scanner scanner;

    public ShowtimeView(
            ShowtimeController showtimeController,
            Scanner scanner
    ) {
        this.showtimeController = showtimeController;
        this.scanner = scanner;
    }

    public void show() {

        System.out.println("========================================================================================");
        System.out.println("                      ____ _____ _  _ _____ ____  ____  ____  _  _ ");
        System.out.println("                     / ___|_   _| || | ____| __ )/ ___|/ ___|| || |");
        System.out.println("                    | |     | | | || |  _| |  _ \\ |  /| |   | || |");
        System.out.println("                    | |___  | | | || | |___| |_) | |__| |___| __ |");
        System.out.println("                     \\____| |_| |_||_|_____|____/\\____|\\____|_||_|");
        System.out.println("                                  THEATRE CLI v1.0");
        System.out.println("========================================================================================");

        System.out.println("[ User: Audrey Yabes (Customer) | Location: CineBook Mall - QC | Session: ACTIVE ]");
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println();
        System.out.println("                             >>> NOW SHOWING MOVIES <<<");
        System.out.println();

        List<Showtime> showtimes = showtimeController.getAllShowtimes();

        if (showtimes.isEmpty()) {
            System.out.println("No movies with available showtimes.");
            System.out.println("----------------------------------------------------------------------------------------");
            return;
        }

        System.out.println(" ID | Title                    | Genre                   | Rating | Duration");
        System.out.println("----+--------------------------+-------------------------+--------+----------");

        for (Showtime showtime : showtimes) {

            System.out.printf(
                    " %2d | %-24s | %-23s | %-6s | %d mins%n",
                    showtime.getMovieId(),
                    showtime.getMovieTitle(),
                    showtime.getGenre(),
                    showtime.getRating(),
                    showtime.getDurationMinutes()
            );
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.print("Select Movie ID to view showtimes (or 'B' to Go Back): ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("B")) {
            return;
        }

        int movieId;

        try {
            movieId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Movie ID.");
            return;
        }

        showMovieShowtimes(movieId);
    }

    private void showMovieShowtimes(int movieId) {

        List<Showtime> showtimes =
                showtimeController.getShowtimesByMovieId(movieId);

        if (showtimes.isEmpty()) {
            System.out.println();
            System.out.println("No available showtimes for this movie.");
            return;
        }

        String movieTitle = showtimes.get(0).getMovieTitle();

        System.out.println();
        System.out.println("                         >>> SHOWTIMES FOR: "
                + movieTitle.toUpperCase() + " <<<");
        System.out.println();

        System.out.println(" Showtime ID | Theater   | Date & Start Time   | End Time            | Price");
        System.out.println("-------------+-----------+---------------------+---------------------+-----------");

        for (Showtime showtime : showtimes) {

            System.out.printf(
                    " %10d | %-9s | %-19s | %-19s | ₱ %7.2f%n",
                    showtime.getId(),
                    showtime.getTheaterName(),
                    showtime.getStartTime(),
                    showtime.getEndTime(),
                    showtime.getTicketPrice()
            );
        }

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.print("Enter Showtime ID to Book (or 'B' to Go Back): ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("B")) {
            return;
        }

        try {
            int showtimeId = Integer.parseInt(input);

            System.out.println();
            System.out.println("Selected Showtime ID: " + showtimeId);

        } catch (NumberFormatException e) {
            System.out.println("Invalid Showtime ID.");
        }
    }
}