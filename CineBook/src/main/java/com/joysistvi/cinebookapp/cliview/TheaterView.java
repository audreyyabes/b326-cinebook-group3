package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.TheaterController;
import com.joysistvi.cinebookapp.model.Theater;

import java.util.List;
import java.util.Scanner;

public class TheaterView {
    private final TheaterController theaterController;
    private final Scanner scanner;

    public TheaterView(TheaterController theaterController) {
        this(theaterController, new Scanner(System.in));
    }

    public TheaterView(TheaterController theaterController, Scanner scanner) {
        this.theaterController = theaterController;
        this.scanner = scanner;
    }

    public void runManageTheater() {
        boolean active = true;
        while (active) {
            page("Admin Portal | Manage Theaters");
            printAdminMenu();
            switch (prompt("Select an option [0-4]: ")) {
                case "1" -> viewAllTheaters();
                case "2" -> runTheaterRegistration();
                case "3" -> updateTheater();
                case "4" -> deleteTheater();
                case "0" -> active = false;
                default -> pause("Invalid choice. Press [ENTER] to continue...");
            }
        }
    }

    private static void printAdminMenu() {
        CliLayout.println("[1] View All Theaters");
        CliLayout.println("[2] Add Theater");
        CliLayout.println("[3] Edit Theater");
        CliLayout.println("[4] Delete Theater");
        CliLayout.println("[0] Back to Admin Portal\n");
    }

    private void viewAllTheaters() {
        page("Admin Portal | Theater Directory");
        List<Theater> theaters = theaterController.handleViewAllTheater();

        if (theaters.isEmpty()) {
            CliLayout.println("No theaters found.");
            pause("Press [ENTER] to return to Theater Management...");
            return;
        }
        CliLayout.table(List.of("ID", "Theater", "Location", "Status"), theaters.stream()
                .map(theater -> List.of(theater.getId(), theater.getName(), theater.getLocation(), theater.getStatus()))
                .toList());
        pause("\nPress [ENTER] to return to Theater Management...");
    }

    public boolean runTheaterRegistration() {
        addTheater();
        return false;
    }

    private void addTheater() {
        page("Admin Portal | Add Theater");
        String name = prompt("Theater name: ");
        String location = prompt("Location: ");
        boolean success = theaterController.handleAddTheater(name, location);
        pause(success ? "Theater added successfully. Press [ENTER] to continue..."
                : "Theater could not be added. Press [ENTER] to continue...");
    }

    private void updateTheater() {
        page("Admin Portal | Edit Theater");
        viewTheaterRows();
        int id = readInt("Theater ID to edit: ");

        Theater current = theaterController.handleReadTheaterById(id);
        if (current == null) {
            CliLayout.println("No Theater found in ID " + id + ". Please check the ID and try again.");
            return;
        }
        String name = prompt("Name [" + current.getName() + "]: ");
        String location = prompt("Location [" + current.getLocation() + "]: ");
        String status = prompt("Status [" + current.getStatus() + "] (active/inactive): ");
        if (name.isBlank())
            name = current.getName();
        if (location.isBlank())
            location = current.getLocation();
        if (status.isBlank())
            status = current.getStatus();

        Theater theater = new Theater(id, name, location, status);

        boolean isSuccess = theaterController.handleUpdateTheater(theater);
        pause(isSuccess ? "Theater updated successfully. Press [ENTER] to continue..."
                : "Theater could not be updated. Press [ENTER] to continue...");
    }

    private void deleteTheater() {
        page("Admin Portal | Delete Theater");
        viewTheaterRows();
        int id = readInt("Theater ID to delete: ");
        if (!"Y".equalsIgnoreCase(prompt("Delete theater " + id + "? (Y/N): "))) {
            return;
        }

        boolean isSuccess = theaterController.handleDeleteUser(id);
        pause(isSuccess ? "Theater deleted. Press [ENTER] to continue..."
                : "Theater could not be deleted. Press [ENTER] to continue...");
    }

    private void viewTheaterRows() {
        List<Theater> theaters = theaterController.handleViewAllTheater();
        CliLayout.table(List.of("ID", "Theater", "Location", "Status"), theaters.stream()
                .map(theater -> List.of(theater.getId(), theater.getName(), theater.getLocation(), theater.getStatus()))
                .toList());
    }

    private void page(String title) {
        Header.print();
        CliLayout.println("[ " + title + " ]");
        CliLayout.println("-".repeat(88));
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

    private void pause(String message) {
        CliLayout.print(message);
        scanner.nextLine();
    }
}
