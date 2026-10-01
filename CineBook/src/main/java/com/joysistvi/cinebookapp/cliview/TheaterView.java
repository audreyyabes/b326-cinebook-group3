package com.joysistvi.cinebookapp.cliview;

import com.joysistvi.cinebookapp.controller.TheaterController;
import com.joysistvi.cinebookapp.model.Theater;

import java.util.List;
import java.util.Scanner;

public class TheaterView {
    private final TheaterController theaterController;
    Scanner scanner = new Scanner(System.in);

    public TheaterView(TheaterController theaterController) {
        this.theaterController = theaterController;

    }

    public void runManageTheater() {
        int theaterChoice;
        borderComponent();
        do {
            printAdminMenu();
            theaterChoice = readInt(scanner);
            switch (theaterChoice) {
                case 1 -> viewAllTheaters();
                case 2 -> runTheaterRegistration();
                case 3 -> updateTheater();
                case 4 -> deleteTheater();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (theaterChoice != 0);

    }

    private static void printAdminMenu() {
        System.out.println("1 - View all theater");
        System.out.println("2 - Add theater");
        System.out.println("3 - Update theater using ID");
        System.out.println("4 - Delete theater using ID ");
        System.out.println("0 - Logout");
        System.out.print("Choice: ");
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
    }

    private void viewAllTheaters() {
        List<Theater> theaters = theaterController.handleViewAllTheater();

        if (theaters.isEmpty()) {
            System.out.println("No theater found.");
            return;
        }
        String border = "+" + "-".repeat(6) + "+" + "-".
                repeat(22) + "+" + "-".repeat(40) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-20s | %-23s | %-11s  |%n", "ID", "Theater Name", "Location", "Status");
        System.out.println(border);

        for (Theater theater : theaters) {
            System.out.printf("| %-4d | %-20s | %-23s | %-12s |%n", theater.getId(), theater.getName(), theater.getLocation(), theater.getStatus());
        }

        System.out.println(border);
    }

    private static void borderComponent() {
        System.out.println("""
                ========================================================================================
                                      ____ _____ _  _ _____ ____  ____  ____  _  _
                                     / ___|_   _| || | ____| __ )/ ___|/ ___|| || |
                                    | |     | | | || |  _| |  _ \\ |  /| |   | || |
                                    | |___  | | | || | |___| |_) | |__| |___| __ |
                                     \\____| |_| |_||_|_____|____/\\____|\\____|_||_|
                ========================================================================================
                [ Portal: Manage Theater]
                ----------------------------------------------------------------------------------------""");
    }

    public boolean runTheaterRegistration(){
        addTheater( scanner,  theaterController);
        return false;
    }

    private void addTheater(Scanner scanner, TheaterController theaterController) {
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Location: ");
        String location = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("[!] Status will automatically assigned: 'active'");
        System.out.println("[✓] Checking email availability...");

        boolean success = theaterController.handleAddTheater(name, location);
        System.out.println(success
                ? "Added successfully!"
                : "Failed to register.");
    }

    private void updateTheater(){
        borderComponent();
        viewAllTheaters();
        System.out.println("Enter theater ID ");
        int id = readInt(scanner);

        Theater current = theaterController.handleReadTheaterById(id);
        if (current == null) {
            System.out.println("No Theater found in ID " + id + ". Please check the ID and try again.");
            return;
        }
        System.out.println("New Theater name [" + current.getName() + "] (press Enter to keep the current.): ");
        String name = scanner.nextLine();
        System.out.println("New Theater location [" + current.getLocation() + "] (press Enter to keep the current.): ");
        String location = scanner.nextLine();
        System.out.println("Status: Active and inactive only");
        System.out.println("New Theater status [" + current.getStatus() + "] (press Enter to keep the current.): ");
        String status = scanner.next();

        Theater theater = new Theater(id, name, location, status);

        boolean isSuccess = theaterController.handleUpdateTheater(theater);
        System.out.println(isSuccess ? "Theater updated successfully." : "Failed to update Theater");

        if (isSuccess) {
            System.out.println();
            viewAllTheaters(); //read-after-write || refresh after mutation
        }
    }

    private void deleteTheater(){
        borderComponent();
        viewAllTheaters();
        System.out.println("Enter Theater ID: ");
        int id = scanner.nextInt();

        boolean isSuccess = theaterController.handleDeleteUser(id);
        System.out.println(isSuccess ? "Theater successfully Delete." : "Failed to Delete Theater");

        if (isSuccess) {
            System.out.println();
            viewAllTheaters();
        }


    }

}





