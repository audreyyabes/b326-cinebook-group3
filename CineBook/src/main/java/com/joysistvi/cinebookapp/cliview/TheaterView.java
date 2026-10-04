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

        System.out.println();
        System.out.println("                    >>> MANAGE THEATER <<<");
        System.out.println();

        System.out.println("  1 - View all theaters");
        System.out.println("  2 - Add theater");
        System.out.println("  3 - Update theater using ID");
        System.out.println("  4 - Delete theater using ID");
        System.out.println("  0 - Logout");
        System.out.println();

        System.out.print("Choice: ");
    }

    private static int readInt(Scanner scanner) {

        while (!scanner.hasNextInt()) {

            System.out.print("Please enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();

        scanner.nextLine();

        return value;
    }

    private void viewAllTheaters() {

        List<Theater> theaters =
                theaterController.handleViewAllTheater();

        if (theaters.isEmpty()) {

            System.out.println();
            System.out.println("No theaters found.");
            return;
        }

        String border =
                "+" + "-".repeat(6)
                        + "+" + "-".repeat(22)
                        + "+" + "-".repeat(40)
                        + "+" + "-".repeat(15)
                        + "+";

        System.out.println();
        System.out.println("                         >>> ALL THEATERS <<<");
        System.out.println();

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-20s | %-38s | %-13s |%n",
                "ID",
                "Theater Name",
                "Location",
                "Status"
        );

        System.out.println(border);

        for (Theater theater : theaters) {

            System.out.printf(
                    "| %-4d | %-20s | %-38s | %-13s |%n",
                    theater.getId(),
                    theater.getName(),
                    theater.getLocation(),
                    theater.getStatus()
            );
        }

        System.out.println(border);
    }

    private static void borderComponent() {

        System.out.println();
        System.out.println("========================================================================================");

        System.out.println("   ██████╗██╗███╗   ██╗███████╗██████╗  ██████╗  ██████╗ ██╗  ██╗");
        System.out.println("  ██╔════╝██║████╗  ██║██╔════╝██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝");
        System.out.println("  ██║     ██║██╔██╗ ██║█████╗  ██████╔╝██║   ██║██║   ██║█████╔╝ ");
        System.out.println("  ██║     ██║██║╚██╗██║██╔══╝  ██╔══██╗██║   ██║██║   ██║██╔═██╗ ");
        System.out.println("  ╚██████╗██║██║ ╚████║███████╗██████╔╝╚██████╔╝╚██████╔╝██║  ██╗");
        System.out.println("   ╚═════╝╚═╝╚═╝  ╚═══╝╚══════╝╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝");
        System.out.println();

        System.out.println(
                "                         MOVIE TICKET BOOKING SYSTEM"
        );

        System.out.println("========================================================================================");

        System.out.println(
                "[ Portal: Admin | Module: Manage Theater | System: CINEBOOK ]"
        );

        System.out.println("----------------------------------------------------------------------------------------");
    }

    public boolean runTheaterRegistration() {

        addTheater(scanner, theaterController);

        return false;
    }

    private void addTheater(
            Scanner scanner,
            TheaterController theaterController
    ) {

        System.out.println();
        System.out.println("                         >>> ADD NEW THEATER <<<");
        System.out.println();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Location: ");
        String location = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println("[!] Status will automatically be assigned: 'active'");
        System.out.println("[✓] Registering theater...");

        boolean success =
                theaterController.handleAddTheater(name, location);

        System.out.println(
                success
                        ? "[✓] Theater added successfully!"
                        : "[!] Failed to register theater."
        );
    }

    private void updateTheater() {

        borderComponent();

        viewAllTheaters();

        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.print("Enter Theater ID: ");

        int id = readInt(scanner);

        Theater current =
                theaterController.handleReadTheaterById(id);

        if (current == null) {

            System.out.println(
                    "No theater found with ID " + id
                            + ". Please check the ID and try again."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "New Theater Name ["
                        + current.getName()
                        + "] (press Enter to keep current): "
        );

        String name = scanner.nextLine();

        if (name.isEmpty()) {
            name = current.getName();
        }

        System.out.println(
                "New Theater Location ["
                        + current.getLocation()
                        + "] (press Enter to keep current): "
        );

        String location = scanner.nextLine();

        if (location.isEmpty()) {
            location = current.getLocation();
        }

        System.out.println();
        System.out.println("Status: Active or Inactive only");

        System.out.println(
                "New Theater Status ["
                        + current.getStatus()
                        + "] (press Enter to keep current): "
        );

        String status = scanner.nextLine();

        if (status.isEmpty()) {
            status = current.getStatus();
        }

        Theater theater =
                new Theater(id, name, location, status);

        boolean isSuccess =
                theaterController.handleUpdateTheater(theater);

        System.out.println(
                isSuccess
                        ? "[✓] Theater updated successfully."
                        : "[!] Failed to update theater."
        );

        if (isSuccess) {

            System.out.println();
            viewAllTheaters();
        }
    }

    private void deleteTheater() {

        borderComponent();

        viewAllTheaters();

        System.out.println();
        System.out.print("Enter Theater ID: ");

        int id = readInt(scanner);

        boolean isSuccess =
                theaterController.handleDeleteUser(id);

        System.out.println(
                isSuccess
                        ? "[✓] Theater successfully deleted."
                        : "[!] Failed to delete theater."
        );

        if (isSuccess) {

            System.out.println();
            viewAllTheaters();
        }
    }
}
