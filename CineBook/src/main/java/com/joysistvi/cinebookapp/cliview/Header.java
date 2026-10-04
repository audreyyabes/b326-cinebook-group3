package com.joysistvi.cinebookapp.cliview;

public class Header {

    private static final int WIDTH = 88;

    private static final String BORDER = "=".repeat(WIDTH);

    private static final String[] LOGO = {
            "   ██████╗██╗███╗   ██╗███████╗██████╗  ██████╗  ██████╗ ██╗  ██╗",
            "  ██╔════╝██║████╗  ██║██╔════╝██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝",
            "  ██║     ██║██╔██╗ ██║█████╗  ██████╔╝██║   ██║██║   ██║█████╔╝ ",
            "  ██║     ██║██║╚██╗██║██╔══╝  ██╔══██╗██║   ██║██║   ██║██╔═██╗ ",
            "  ╚██████╗██║██║ ╚████║███████╗██████╔╝╚██████╔╝╚██████╔╝██║  ██╗",
            "   ╚═════╝╚═╝╚═╝  ╚═══╝╚══════╝╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝"
    };

    private static final String SUBTITLE = "THEATRE CLI v1.0";

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
