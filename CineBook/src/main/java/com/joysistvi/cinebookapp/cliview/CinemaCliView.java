package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.BookingController;
import com.joysistvi.cinebookapp.controller.BookingSeatsController;
import com.joysistvi.cinebookapp.controller.MoviesController;
import com.joysistvi.cinebookapp.controller.PaymentController;
import com.joysistvi.cinebookapp.controller.SeatMapController;
import com.joysistvi.cinebookapp.controller.ShowtimeController;
import com.joysistvi.cinebookapp.controller.TheaterController;
import com.joysistvi.cinebookapp.controller.UserBookingController;
import com.joysistvi.cinebookapp.controller.UsersController;
import com.joysistvi.cinebookapp.controller.UsersRegistrationController;
import com.joysistvi.cinebookapp.model.Movies;
import com.joysistvi.cinebookapp.model.Showtime;
import com.joysistvi.cinebookapp.model.ShowtimeSchedule;
import com.joysistvi.cinebookapp.model.Theater;
import com.joysistvi.cinebookapp.model.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CinemaCliView {

    private static final int WIDTH = 88;
    private static final String DIVIDER = "-".repeat(WIDTH);

    private final Scanner scanner;
    private final LoginView loginView;
    private final UsersRegistraionView registrationView;
    private final MoviesController moviesController;
    private final ShowtimeController showtimeController;
    private final UserBookingController userBookingController;
    private final BookingController bookingController;
    private final BookingSeatsController bookingSeatsController;
    private final PaymentController paymentController;
    private final TheaterController theaterController;
    private final SeatMapController seatMapController;
    private final UsersController accountController;
    private final UsersRegistrationController usersController;

    public CinemaCliView(Scanner scanner, LoginView loginView, UsersRegistraionView registrationView,
            MoviesController moviesController, ShowtimeController showtimeController,
            UserBookingController userBookingController, BookingController bookingController,
            BookingSeatsController bookingSeatsController, PaymentController paymentController,
            TheaterController theaterController, SeatMapController seatMapController,
            UsersController accountController,
            UsersRegistrationController usersController) {
        this.scanner = scanner;
        this.loginView = loginView;
        this.registrationView = registrationView;
        this.moviesController = moviesController;
        this.showtimeController = showtimeController;
        this.userBookingController = userBookingController;
        this.bookingController = bookingController;
        this.bookingSeatsController = bookingSeatsController;
        this.paymentController = paymentController;
        this.theaterController = theaterController;
        this.seatMapController = seatMapController;
        this.accountController = accountController;
        this.usersController = usersController;
    }

    public void run() {
        boolean running = true;
        while (running) {
            Header.print();
            CliLayout.println("[ System: Online | Database: cinebook_db | Session: UNAUTHENTICATED ]");
            CliLayout.println(DIVIDER);
            CliLayout.println("\n" + centered("WELCOME TO CINEBOOK TICKETING SYSTEM") + "\n");
            CliLayout.println(centered("[1] Login"));
            CliLayout.println(centered("[2] Register New Customer Account"));
            CliLayout.println(centered("[3] Browse Movies as Guest"));
            CliLayout.println(centered("[4] Exit Application"));
            CliLayout.println("\n" + DIVIDER);

            switch (prompt("Select an option [1-4]: ")) {
                case "1" -> safelyRun(() -> loginView.show().ifPresent(this::showPortal));
                case "2" -> safelyRun(() -> {
                    registrationView.runUserRegistration();
                    pause("Press [ENTER] to return to the Welcome Screen...");
                });
                case "3" -> safelyRun(this::browseMovies);
                case "4" -> running = false;
                default -> pause("Invalid option. Press [ENTER] to try again...");
            }
        }
        CliLayout.println("Goodbye!");
    }

    private void showPortal(Users user) {
        if ("admin".equalsIgnoreCase(user.getRole())) {
            showAdminPortal(user);
        } else {
            showCustomerPortal(user);
        }
    }

    private void showCustomerPortal(Users user) {
        boolean active = true;
        while (active) {
            page("User: " + user.getName() + " | Role: CUSTOMER");
            CliLayout.println("\n" + centered(">>> CUSTOMER PORTAL <<<") + "\n");
            CliLayout.println("[1] Browse Movies & Reserve Tickets");
            CliLayout.println("[2] View Showtimes Schedule");
            CliLayout.println("[3] My Booking History & Payment Receipts");
            CliLayout.println("[4] Account Settings");
            CliLayout.println("[5] Logout\n");
            switch (prompt("Enter selection [1-5]: ")) {
                case "1" -> safelyRun(() -> browseMovies(user, true));
                case "2" -> safelyRun(this::viewShowtimesSchedule);
                case "3" -> safelyRun(() -> new UserBookingView(userBookingController, scanner).display(user.getId()));
                case "4" -> safelyRun(() -> accountSettings(user));
                case "5" -> active = false;
                default -> pause("Invalid selection. Press [ENTER] to continue...");
            }
        }
    }

    private void showAdminPortal(Users user) {
        boolean active = true;
        while (active) {
            page("User: " + user.getName() + " | Role: ADMIN | System Controls Enabled");
            CliLayout.println("\n" + centered(">>> ADMIN PORTAL <<<") + "\n");
            CliLayout.println("[1] Manage Movies (Add, Edit, Set Active/Inactive Status)");
            CliLayout.println("[2] Schedule Showtimes & Set Pricing");
            CliLayout.println("[3] Manage Theaters & Seat Maps");
            CliLayout.println("[4] View All Bookings & Revenue Reports");
            CliLayout.println("[5] Process Pending Payments / Confirm Bookings");
            CliLayout.println("[6] Manage User Accounts (Customers & Admins)");
            CliLayout.println("[7] Logout\n");
            switch (prompt("Enter selection [1-7]: ")) {
                case "1" -> safelyRun(this::manageMovies);
                case "2" -> safelyRun(this::scheduleShowtime);
                case "3" -> safelyRun(this::manageTheatersAndSeatMaps);
                case "4" -> safelyRun(() -> new BookingView(bookingController, scanner).showBookingAudit());
                case "5" -> safelyRun(() -> new BookingView(bookingController, scanner).processPendingPayments());
                case "6" -> safelyRun(() -> manageUserAccounts(user));
                case "7" -> active = false;
                default -> pause("Invalid selection. Press [ENTER] to continue...");
            }
        }
    }

    private void manageMovies() {
        boolean active = true;
        while (active) {
            page("Admin Portal | Movie Management");
            List<Movies> movies = moviesController.handleAllMovies();
            CliLayout.table(List.of("ID", "Title", "Genre", "Rating", "Duration", "Status"), movies.stream()
                    .map(movie -> List.of(movie.getId(), movie.getTitle(), movie.getGenre(), movie.getRating(),
                            movie.getDuration() + " mins", movie.getStatus()))
                    .toList());
            CliLayout.println("\n[A] Add | [E] Edit | [T] Toggle Active/Inactive | [D] Delete | [B] Back");
            switch (prompt("Select Action: ").toUpperCase()) {
                case "A" -> addMovie();
                case "E" -> editMovie();
                case "T" -> toggleMovieStatus();
                case "D" -> deleteMovie();
                case "B" -> active = false;
                default -> pause("Invalid action. Press [ENTER] to continue...");
            }
        }
    }

    private void addMovie() {
        page("Admin Portal | Add Movie");
        String title = prompt("Title: ");
        String genre = prompt("Genre: ");
        String rating = prompt("Rating: ");
        int duration = readInt("Duration in minutes: ");
        Movies movie = new Movies(0, title, genre, rating, duration, "active");
        pause(moviesController.handleSaveMovies(movie)
                ? "Movie added. Press [ENTER] to continue..."
                : "Movie could not be added. Press [ENTER] to continue...");
    }

    private void editMovie() {
        int id = readInt("Movie ID to edit: ");
        Movies movie = moviesController.handleReadMoviesById(id);
        if (movie == null) {
            pause("Movie not found. Press [ENTER] to continue...");
            return;
        }
        String title = prompt("Title [" + movie.getTitle() + "]: ");
        String genre = prompt("Genre [" + movie.getGenre() + "]: ");
        String rating = prompt("Rating [" + movie.getRating() + "]: ");
        String duration = prompt("Duration in minutes [" + movie.getDuration() + "]: ");
        if (!title.isBlank())
            movie.setTitle(title);
        if (!genre.isBlank())
            movie.setGenre(genre);
        if (!rating.isBlank())
            movie.setRating(rating);
        if (!duration.isBlank()) {
            try {
                movie.setDuration(Integer.parseInt(duration));
            } catch (NumberFormatException e) {
                pause("Duration must be a number. Press [ENTER] to continue...");
                return;
            }
        }
        pause(moviesController.handleUpdateMovies(movie)
                ? "Movie updated. Press [ENTER] to continue..."
                : "Movie could not be updated. Press [ENTER] to continue...");
    }

    private void toggleMovieStatus() {
        int id = readInt("Movie ID to toggle: ");
        Movies movie = moviesController.handleReadMoviesById(id);
        if (movie == null) {
            pause("Movie not found. Press [ENTER] to continue...");
            return;
        }
        movie.setStatus("active".equalsIgnoreCase(movie.getStatus()) ? "inactive" : "active");
        pause(moviesController.handleUpdateMovies(movie)
                ? "Movie is now " + movie.getStatus() + ". Press [ENTER] to continue..."
                : "Movie status could not be changed. Press [ENTER] to continue...");
    }

    private void deleteMovie() {
        int id = readInt("Movie ID to delete: ");
        if (!"Y".equalsIgnoreCase(prompt("Delete movie " + id + "? (Y/N): ")))
            return;
        pause(moviesController.handleDeleteMovies(id)
                ? "Movie deleted. Press [ENTER] to continue..."
                : "Movie could not be deleted. It may have showtimes. Press [ENTER] to continue...");
    }

    private void scheduleShowtime() {
        page("Admin Portal | Schedule Showtime");
        List<Movies> movies = moviesController.handleAllMovies().stream()
                .filter(movie -> "active".equalsIgnoreCase(movie.getStatus())).toList();
        List<Theater> theaters = theaterController.handleViewAllTheater().stream()
                .filter(theater -> "active".equalsIgnoreCase(theater.getStatus())).toList();
        if (movies.isEmpty() || theaters.isEmpty()) {
            pause("An active movie and theater are required. Press [ENTER] to continue...");
            return;
        }
        CliLayout.println("Active movies:");
        CliLayout.table(List.of("ID", "Title", "Duration"), movies.stream()
                .map(movie -> List.of(movie.getId(), movie.getTitle(), movie.getDuration() + " mins"))
                .toList());
        int movieId = readInt("Movie ID: ");
        Movies movie = movies.stream().filter(item -> item.getId() == movieId).findFirst().orElse(null);
        CliLayout.println("\nActive theaters:");
        CliLayout.table(List.of("ID", "Theater", "Location"), theaters.stream()
                .map(theater -> List.of(theater.getId(), theater.getName(), theater.getLocation()))
                .toList());
        int theaterId = readInt("Theater ID: ");
        Theater theater = theaters.stream().filter(item -> item.getId() == theaterId).findFirst().orElse(null);
        if (movie == null || theater == null) {
            pause("Movie or theater selection is invalid. Press [ENTER] to continue...");
            return;
        }
        LocalDateTime start;
        try {
            start = LocalDateTime.parse(prompt("Start time (yyyy-MM-dd HH:mm): "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (RuntimeException e) {
            pause("Invalid date/time. Press [ENTER] to continue...");
            return;
        }
        double price = readDouble("Ticket price: ");
        Showtime showtime = new Showtime(0, movie.getId(), theater.getId(), start,
                start.plusMinutes(movie.getDuration()), price, "scheduled");
        pause(showtimeController.handleCreateShowtime(showtime)
                ? "Showtime scheduled. Press [ENTER] to continue..."
                : "Showtime could not be scheduled. Check the date and price. Press [ENTER] to continue...");
    }

    private void manageTheatersAndSeatMaps() {
        boolean active = true;
        while (active) {
            page("Admin Portal | Theaters & Seat Maps");
            CliLayout.println("[1] Manage Theater Details");
            CliLayout.println("[2] Manage Theater Seat Maps");
            CliLayout.println("[B] Back\n");
            switch (prompt("Select Action: ").toUpperCase()) {
                case "1" -> safelyRun(() -> new TheaterView(theaterController, scanner).runManageTheater());
                case "2" -> safelyRun(() -> new SeatMapView(seatMapController, theaterController, scanner).run());
                case "B" -> active = false;
                default -> pause("Invalid action. Press [ENTER] to continue...");
            }
        }
    }

    private void manageUserAccounts(Users currentAdmin) {
        boolean active = true;
        while (active) {
            page("Admin Portal | Manage User Accounts");
            CliLayout.table(List.of("ID", "Name", "Email", "Role"), usersController.handleViewAllUsers().stream()
                    .map(account -> List.of(account.getId(), account.getName(), account.getEmail(), account.getRole()))
                    .toList());
            CliLayout.println("\n[A] Add Account | [D] Delete Account | [B] Back");
            switch (prompt("Select Action: ").toUpperCase()) {
                case "A" -> addAccount();
                case "D" -> deleteAccount(currentAdmin);
                case "B" -> active = false;
                default -> pause("Invalid action. Press [ENTER] to continue...");
            }
        }
    }

    private void addAccount() {
        String role = prompt("Account role [customer/admin]: ").toLowerCase();
        if (!role.equals("customer") && !role.equals("admin")) {
            pause("Role must be customer or admin. Press [ENTER] to continue...");
            return;
        }
        String name = prompt("Full name: ");
        String email = prompt("Email: ");
        String password = prompt("Password (at least 8 characters): ");
        String confirmation = prompt("Confirm password: ");
        if (!password.equals(confirmation)) {
            pause("Passwords do not match. Press [ENTER] to continue...");
            return;
        }
        pause(usersController.handleRegister(name, email, password, role)
                ? "Account created as " + role + ". Press [ENTER] to continue..."
                : "Account could not be created. Check fields and whether the email is already registered. Press [ENTER] to continue...");
    }

    private void deleteAccount(Users currentAdmin) {
        int id = readInt("User ID to delete: ");
        if (id == currentAdmin.getId()) {
            pause("You cannot delete the account currently in use. Press [ENTER] to continue...");
            return;
        }
        if (!"Y".equalsIgnoreCase(prompt("Delete user " + id + "? (Y/N): ")))
            return;
        pause(usersController.handleDeleteUser(id)
                ? "User deleted. Press [ENTER] to continue..."
                : "User could not be deleted. The last admin account and accounts with bookings are protected. Press [ENTER] to continue...");
    }

    private void browseMovies() {
        browseMovies(null, false);
    }

    private void browseMovies(Users user, boolean allowReservation) {
        page(user == null ? "Guest | Catalog View"
                : "User: " + user.getName() + " (Customer) | Catalog View");
        CliLayout.println("\n" + centered(">>> NOW SHOWING MOVIES <<<") + "\n");
        List<Movies> movies = moviesController.handleAllMovies().stream()
                .filter(movie -> "active".equalsIgnoreCase(movie.getStatus()))
                .toList();
        if (movies.isEmpty()) {
            pause("No movies found. Press [ENTER] to return...");
            return;
        }
        CliLayout.table(List.of("ID", "Title", "Genre", "Rating", "Duration"), movies.stream()
                .map(movie -> List.of(movie.getId(), movie.getTitle(), movie.getGenre(), movie.getRating(),
                        movie.getDuration() + " mins"))
                .toList());

        String selection = prompt("Select a Movie ID" + (allowReservation ? " to reserve" : " to view showtimes")
                + " or B to go back: ");
        if (selection.equalsIgnoreCase("B")) {
            return;
        }
        try {
            int movieId = Integer.parseInt(selection);
            List<ShowtimeSchedule> showtimes = showtimeController.handleViewShowtimesByMovieId(movieId);
            if (showtimes.isEmpty()) {
                pause("No scheduled showtimes. Press [ENTER] to return...");
                return;
            }
            Movies movie = movies.stream().filter(item -> item.getId() == movieId).findFirst().orElseThrow();
            CliLayout.println("\n" + centered(">>> SHOWTIMES FOR: " + movie.getTitle().toUpperCase() + " <<<") + "\n");
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            CliLayout.table(List.of("Showtime ID", "Theater", "Date & Start Time", "End Time", "Price"),
                    showtimes.stream().map(showtime -> List.of(showtime.getId(), showtime.getTheaterName(),
                            showtime.getStartTime().format(dateTimeFormat),
                            showtime.getEndTime().format(dateTimeFormat),
                            String.format("₱ %.2f", showtime.getTicketPrice())))
                            .toList());
            if (allowReservation && user != null) {
                String showtimeSelection = prompt("Enter Showtime ID to choose seats or B to go back: ");
                if (!showtimeSelection.equalsIgnoreCase("B")) {
                    int showtimeId = Integer.parseInt(showtimeSelection);
                    ShowtimeSchedule selected = showtimes.stream()
                            .filter(showtime -> showtime.getId() == showtimeId).findFirst().orElse(null);
                    if (selected == null) {
                        pause("Showtime not found. Press [ENTER] to return...");
                    } else {
                        selectSeats(user, movie, selected);
                    }
                }
            } else {
                pause("Press [ENTER] to return...");
            }
        } catch (NumberFormatException e) {
            pause("Invalid movie ID. Press [ENTER] to return...");
        }
    }

    private void viewShowtimesSchedule() {
        page("Customer Portal | Showtime Schedule");
        List<List<?>> rows = new ArrayList<>();
        for (Movies movie : moviesController.handleAllMovies()) {
            if (!"active".equalsIgnoreCase(movie.getStatus()))
                continue;
            for (ShowtimeSchedule showtime : showtimeController.handleViewShowtimesByMovieId(movie.getId())) {
                rows.add(List.of(movie.getTitle(), showtime.getId(), showtime.getTheaterName(),
                        showtime.getStartTime(), showtime.getEndTime(),
                        String.format("₱%,.2f", showtime.getTicketPrice())));
            }
        }
        if (rows.isEmpty()) {
            pause("No scheduled showtimes are available. Press [ENTER] to return...");
            return;
        }
        CliLayout.table(List.of("Movie", "Showtime ID", "Theater", "Start Time", "End Time", "Price"), rows);
        pause("Press [ENTER] to return to Customer Portal...");
    }

    private void selectSeats(Users user, Movies movie, ShowtimeSchedule showtime) {
        List<String> seatResults = userBookingController.getSeatsForShowtime(showtime.getId());
        Map<String, Integer> seatIds = new LinkedHashMap<>();
        Map<String, String> seatStatuses = new LinkedHashMap<>();
        for (String seat : seatResults) {
            String[] values = seat.split("\\s*\\|\\s*");
            if (values.length >= 3) {
                seatIds.put(values[1].toUpperCase(), Integer.parseInt(values[0]));
                seatStatuses.put(values[1].toUpperCase(), values[2]);
            }
        }
        if (seatIds.isEmpty()) {
            pause("No seats are configured for this theater. Press [ENTER] to return...");
            return;
        }
        page("Theater: " + showtime.getTheaterName() + " | Movie: " + movie.getTitle());
        CliLayout.printf("[ Theater: %s | Movie: %s | Ticket Rate: ₱%.2f ]%n", showtime.getTheaterName(),
                movie.getTitle(), showtime.getTicketPrice());
        CliLayout.println("\n                       +----------------------------------+");
        CliLayout.println("                       |           S C R E E N            |");
        CliLayout.println("                       +----------------------------------+\n");
        Map<String, List<String>> rows = new LinkedHashMap<>();
        for (String code : seatIds.keySet()) {
            int rowEnd = 0;
            while (rowEnd < code.length() && Character.isLetter(code.charAt(rowEnd)))
                rowEnd++;
            String row = code.substring(0, rowEnd);
            rows.computeIfAbsent(row, ignored -> new ArrayList<>()).add(code);
        }
        rows.forEach((row, codes) -> {
            CliLayout.printf("         Row %-2s:    ", row);
            for (String code : codes) {
                CliLayout.printf("[%s] ", "BOOKED".equalsIgnoreCase(seatStatuses.get(code)) ? " X " : code);
            }
            CliLayout.println();
        });
        CliLayout.println("\n" + DIVIDER);
        CliLayout.println("Legend: [ A1 ] Available  |  [ X ] Reserved / Occupied");
        CliLayout.println(DIVIDER);
        String selection = prompt("Enter seat codes separated by commas (e.g., A3, A4) or 'B' to cancel: ");
        if (selection.equalsIgnoreCase("B"))
            return;

        List<Integer> selectedIds = new ArrayList<>();
        List<String> selectedCodes = new ArrayList<>();
        for (String rawCode : selection.split(",")) {
            String code = rawCode.trim().toUpperCase();
            if (!seatIds.containsKey(code) || !"AVAILABLE".equalsIgnoreCase(seatStatuses.get(code))) {
                pause("Seat " + code + " is unavailable or unknown. Press [ENTER] to return...");
                return;
            }
            if (selectedIds.contains(seatIds.get(code))) {
                pause("A seat was selected more than once. Press [ENTER] to return...");
                return;
            }
            selectedIds.add(seatIds.get(code));
            selectedCodes.add(code);
        }
        Theater theater = theaterController.handleViewAllTheater().stream()
                .filter(item -> item.getName().equals(showtime.getTheaterName())).findFirst().orElse(null);
        String theaterLabel = showtime.getTheaterName();
        if (theater != null && theater.getLocation() != null && !theater.getLocation().isBlank()) {
            theaterLabel += " (" + theater.getLocation() + ")";
        }
        DateTimeFormatter receiptTime = DateTimeFormatter.ofPattern("yyyy-MM-dd ' @ ' hh:mm a");
        String showtimeLabel = showtime.getStartTime().format(receiptTime) + " - "
                + showtime.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        new PaymentView(bookingController, bookingSeatsController, paymentController, scanner).show(
                user.getName(), user.getId(), showtime.getId(), movie.getTitle(), theaterLabel, showtimeLabel,
                selectedCodes, selectedIds, showtime.getTicketPrice());
    }

    private void accountSettings(Users user) {
        boolean active = true;
        while (active) {
            page("Customer Portal | Account Settings");
            CliLayout.println("Name  : " + user.getName());
            CliLayout.println("Email : " + user.getEmail());
            CliLayout.println("\n[1] Update Name and Email");
            CliLayout.println("[2] Change Password");
            CliLayout.println("[B] Back\n");
            switch (prompt("Select an option: ").toUpperCase()) {
                case "1" -> updateAccountProfile(user);
                case "2" -> changeAccountPassword(user);
                case "B" -> active = false;
                default -> pause("Invalid selection. Press [ENTER] to continue...");
            }
        }
    }

    private void updateAccountProfile(Users user) {
        String name = prompt("Full name [" + user.getName() + "]: ");
        String email = prompt("Email [" + user.getEmail() + "]: ");
        if (name.isBlank())
            name = user.getName();
        if (email.isBlank())
            email = user.getEmail();
        if (accountController.updateProfile(user, name, email)) {
            user.setName(name);
            user.setEmail(email);
            pause("Account details updated. Press [ENTER] to continue...");
        } else {
            pause("Account details were not changed. The email may already be in use. Press [ENTER] to continue...");
        }
    }

    private void changeAccountPassword(Users user) {
        String currentPassword = prompt("Current password: ");
        String newPassword = prompt("New password (at least 8 characters): ");
        String confirmation = prompt("Confirm new password: ");
        if (!newPassword.equals(confirmation)) {
            pause("Passwords do not match. Press [ENTER] to continue...");
            return;
        }
        pause(accountController.changePassword(user, currentPassword, newPassword)
                ? "Password changed. Press [ENTER] to continue..."
                : "Password was not changed. Check the current password and new password length. Press [ENTER] to continue...");
    }

    private void page(String title) {
        Header.print();
        CliLayout.println("[ " + title + " ]");
        CliLayout.println(DIVIDER);
    }

    private String centered(String value) {
        return " ".repeat(Math.max((WIDTH - value.length()) / 2, 0)) + value;
    }

    private String prompt(String label) {
        CliLayout.print(label);
        return scanner.nextLine().trim();
    }

    private int readInt(String label) {
        try {
            return Integer.parseInt(prompt(label));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double readDouble(String label) {
        try {
            return Double.parseDouble(prompt(label));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void pause(String message) {
        CliLayout.print(message);
        scanner.nextLine();
    }

    private void safelyRun(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            CliLayout.println("\n[!] This action could not be completed: " + exception.getMessage());
            pause("Press [ENTER] to return to the menu...");
        }
    }
}
