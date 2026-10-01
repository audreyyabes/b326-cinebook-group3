package com.joysistvi.cinebookapp.cliview;

public class Header {

    private static final int WIDTH = 88;

    private static final String BORDER = "=".repeat(WIDTH);

    private static final String[] LOGO = {
            "  ____ _            ____              _    ",
            " / ___(_)_ __   ___| __ )  ___   ___ | | __",
            "| |   | | '_ \\ / _ \\  _ \\ / _ \\ / _ \\| |/ /",
            "| |___| | | | |  __/ |_) | (_) | (_) |   < ",
            " \\____|_|_| |_|\\___|____/ \\___/ \\___/|_|\\_\\"
    };

    private static final String SUBTITLE = "CINEBOOK CLI v1.0";

    public static void print() {
        clearScreen();
        System.out.println(BORDER);
        for (String line : LOGO) {
            System.out.println(center(line));
        }
        System.out.println(center(SUBTITLE));
        System.out.println(BORDER);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }

    private static String center(String text) {
        int padding = Math.max((WIDTH - text.length()) / 2, 0);
        return " ".repeat(padding) + text;
    }
}
