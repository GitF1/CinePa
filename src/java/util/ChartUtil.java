/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ChartUtil {

    private final Map<String, String> CHART_COLORS = new HashMap<>();

    public ChartUtil() {

        CHART_COLORS.put("red", "rgb(255, 99, 132)");
        CHART_COLORS.put("green", "rgb(75, 192, 192)");
        CHART_COLORS.put("blue", "rgb(54, 162, 235)");
        CHART_COLORS.put("orange", "rgb(255, 159, 64)");
        CHART_COLORS.put("yellow", "rgb(255, 205, 86)");
        CHART_COLORS.put("purple", "rgb(153, 102, 255)");
        CHART_COLORS.put("grey", "rgb(201, 203, 207)");
        CHART_COLORS.put("lightblue", "rgb(173, 216, 230)");
        CHART_COLORS.put("pink", "rgb(255, 192, 203)");
        CHART_COLORS.put("brown", "rgb(165, 42, 42)");
        CHART_COLORS.put("cyan", "rgb(0, 255, 255)");
        CHART_COLORS.put("magenta", "rgb(255, 0, 255)");

    }

    public static List<String> months(int count) {

        List<String> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            values.add(Month.of(i + 1).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        return values;

    }

    // Overloaded method to generate a default list of 12 month names
    public static List<String> months() {
        return months(12);
    }

    // Method to generate a list of random numbers
    public static List<Double> numbers(int count, double min, double max, int decimals, double continuity) {
        List<Double> data = new ArrayList<>();
        Random random = new Random();
        double dfactor = Math.pow(10, decimals);

        for (int i = 0; i < count; i++) {
            double value = min + (random.nextDouble() * (max - min));
            if (random.nextDouble() <= continuity) {
                data.add(Math.round(dfactor * value) / dfactor);
            } else {
                data.add(null);
            }
        }

        return data;
    }

    // Overloaded method to generate a default list of random numbers
    public static List<Double> numbers() {
        return numbers(8, 0, 100, 8, 1);
    }

    // Method to simulate the transparentize function
    public static String transparentize(String color, double opacity) {
        int alpha = (int) (opacity * 255);
        return color + Integer.toHexString(alpha);
    }

    // Method to return chart colors
    public String getChartColor(String colorName) {
        return CHART_COLORS.getOrDefault(colorName.toLowerCase(), "rgb(0, 0, 0)");
    }

    public List<String> getChartColors(int amount) {
        List<String> colors = new ArrayList<>(amount);
        int index = 0;
        for (String color : CHART_COLORS.keySet()) {
            if (index >= amount) {
                break;
            }
            colors.add(CHART_COLORS.get(color));
            index++;
        }
        return colors;
    }

    public List<String> getBestChartColorsDogount(int numColors) {

        List<String> recommendedColors = new ArrayList<>();

        // Add some commonly recommended colors
        recommendedColors.add("#4CAF50"); // Green
        recommendedColors.add("#2196F3"); // Blue
        recommendedColors.add("#009688"); // Teal
        recommendedColors.add("#FFC107"); // Amber
        recommendedColors.add("#9C27B0"); // Purple
        recommendedColors.add("#FF5722"); // Deep Orange
        recommendedColors.add("#00BCD4"); // Cyan
        recommendedColors.add("#673AB7"); // Deep Purple
        recommendedColors.add("#FF9800"); // Orange

        recommendedColors.add("#795548"); // Brown

        // If more colors are needed, generate random complementary colors
        Random random = new Random();
        while (recommendedColors.size() < numColors) {
            // Generate random RGB values
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            // Convert RGB to hexadecimal color representation
            String hexColor = String.format("#%02X%02X%02X", r, g, b);
            recommendedColors.add(hexColor);
        }

        // Return only the required number of colors
        return recommendedColors.subList(0, numColors);
    }

    public static Integer getAmountDateOfMonth(Integer month, Integer year) {

        if (year == null) {
            year = new Time().getYear();
        }

        if (month == null) {
            month = new Time().getMonth();
        }

        YearMonth yearMonth = YearMonth.of(year, month);

        return yearMonth.lengthOfMonth();
    }

    public static String getCurrentMonthName() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);
        String monthName = currentDate.format(formatter);
        return monthName;
    }

    public static Integer getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public static List<Integer> getListDateInMonth(Integer month, Integer year) {
        
        int dateOfMonth = getAmountDateOfMonth(month, year);
        List<Integer> listDateOfMonth = new ArrayList<>(dateOfMonth);

        for (int i = 1; i <= dateOfMonth; i++) {
            listDateOfMonth.add(i);
        }
        
        return listDateOfMonth;
    }
}
