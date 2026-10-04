package com.joysistvi.cinebookapp.cliview;

import java.util.ArrayList;
import java.util.List;

public final class CliLayout {

    private CliLayout() {
    }

    public static void print(String value) {
        System.out.print(value);
    }

    public static void println() {
        System.out.println();
    }

    public static void println(String value) {
        System.out.println(value);
    }

    public static void printf(String format, Object... arguments) {
        System.out.printf(format, arguments);
    }

    public static void table(List<String> headers, List<? extends List<?>> rows) {
        if (headers == null || headers.isEmpty()) {
            return;
        }

        List<? extends List<?>> safeRows = rows == null ? List.of() : rows;
        int[] widths = headers.stream().mapToInt(CliLayout::cellTextLength).toArray();
        List<List<String>> values = new ArrayList<>(safeRows.size());

        for (List<?> row : safeRows) {
            List<String> cells = new ArrayList<>(headers.size());
            for (int column = 0; column < headers.size(); column++) {
                Object value = row != null && column < row.size() ? row.get(column) : "";
                String text = normalizeCell(value);
                cells.add(text);
                widths[column] = Math.max(widths[column], text.length());
            }
            values.add(cells);
        }

        printTableBorder(widths);
        printTableRow(headers, widths, false);
        printTableBorder(widths);
        for (int rowIndex = 0; rowIndex < values.size(); rowIndex++) {
            printTableRow(values.get(rowIndex), widths, true, safeRows.get(rowIndex));
        }
        printTableBorder(widths);
    }

    private static void printTableRow(List<?> cells, int[] widths, boolean allowNumericAlignment) {
        printTableRow(cells, widths, allowNumericAlignment, cells);
    }

    private static void printTableRow(List<?> cells, int[] widths, boolean allowNumericAlignment, Object sourceRow) {
        System.out.print("| ");
        for (int column = 0; column < widths.length; column++) {
            Object value = column < cells.size() ? cells.get(column) : "";
            String text = normalizeCell(value);
            Object sourceValue = sourceRow instanceof List<?> source && column < source.size()
                    ? source.get(column) : value;
            if (allowNumericAlignment && sourceValue instanceof Number) {
                System.out.printf("%" + widths[column] + "s", text);
            } else {
                System.out.printf("%-" + widths[column] + "s", text);
            }
            System.out.print(" | ");
        }
        System.out.println();
    }

    private static void printTableBorder(int[] widths) {
        System.out.print("+");
        for (int width : widths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();
    }

    private static int cellTextLength(String value) {
        return normalizeCell(value).length();
    }

    private static String normalizeCell(Object value) {
        return value == null ? "" : value.toString().replace('\n', ' ').replace('\r', ' ');
    }
}