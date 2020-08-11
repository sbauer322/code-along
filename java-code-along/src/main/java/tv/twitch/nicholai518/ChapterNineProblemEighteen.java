package tv.twitch.nicholai518;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Gas Prices
 *
 * Probably some formatting rounding errors when printing, but that's what you get for using doubles to track money.
 *
 * Generally took the approach of ingesting all the data and performing separate computations and building the necessary data structure on the fly (as opposed to building all the data structures as we read the file) mainly for readability and clarity.
 *
 * Outputs:
 *
 * Average price per year
 * Average price per month
 * Highest and lowest prices per year
 * List of prices, ordered low to high
 * List of prices, ordered high to low
 */
public class ChapterNineProblemEighteen {
    public void run() {
        List<DatePricePair> allDatePricePairs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        var filepath = "src/main/resources/tv/twitch/nicholai518/ch9prb18.txt";
        try {
            Files.lines(Path.of(filepath)).forEachOrdered((line) -> {
                String[] content = line.split(":");
                LocalDate date = LocalDate.parse(content[0], formatter);
                double price = Double.parseDouble(content[1]);
                allDatePricePairs.add(new DatePricePair(date, price));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, List<DatePricePair>> byYear = groupByYear(allDatePricePairs);
        Map<Integer, List<DatePricePair>> byYearAndMonth = groupByYearAndMonth(allDatePricePairs);

        // comment out some calculate methods to reduce console output
        calculateAveragePricePerYear(byYear);
        calculateAveragePricePerMonth(byYearAndMonth);
        calculateHighestPerYear(byYear);
        calculateLowestPerYear(byYear);
    }

    private Map<Integer, List<DatePricePair>> groupByYear(List<DatePricePair> allDatePricePairs) {
        Map<Integer, List<DatePricePair>> byYears = new HashMap<>();
        for (DatePricePair datePricePair : allDatePricePairs) {
            int year = datePricePair.getDate().getYear();
            byYears.computeIfAbsent(year, integer -> new ArrayList<>());
            byYears.computeIfPresent(year, (integer, datePricePairs) -> {
                datePricePairs.add(datePricePair);
                return datePricePairs;
            });
        }
        return byYears;
    }

    private Map<Integer, List<DatePricePair>> groupByYearAndMonth(List<DatePricePair> allDatePricePairs) {
        Map<Integer, List<DatePricePair>> byYears = new HashMap<>();
        for (DatePricePair datePricePair : allDatePricePairs) {
            // bad practice trick as i'm being lazy
            int yearMonth = datePricePair.getDate().getYear() * 100 + datePricePair.getDate().getMonthValue();
            byYears.computeIfAbsent(yearMonth, integer -> new ArrayList<>());
            byYears.computeIfPresent(yearMonth, (integer, datePricePairs) -> {
                datePricePairs.add(datePricePair);
                return datePricePairs;
            });
        }
        return byYears;
    }

    private void calculateAveragePricePerYear(Map<Integer, List<DatePricePair>> byYears) {
        System.out.println("Average Price Per Year:");
        for (var entry : byYears.entrySet()) {
            Double sum = entry.getValue().stream().map(DatePricePair::getPrice).reduce(0.0, Double::sum);
            double average = sum / entry.getValue().size();
            System.out.println(entry.getKey() + " : " + String.format("%.2f", average));
        }
    }

    private void calculateAveragePricePerMonth(Map<Integer, List<DatePricePair>> byYearAndMonth) {
        System.out.println("Average Price Per Month:");
        // lol.
        byYearAndMonth.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(entry -> {
            Double sum = entry.getValue().stream().map(DatePricePair::getPrice).reduce(0.0, Double::sum);
            double average = sum / entry.getValue().size();
            // undoing the trick from earlier
            int year = entry.getKey() / 100;
            int month = entry.getKey() % 100;
            System.out.println(year + " " + month + " : " + String.format("%.2f", average));
        });
    }

    private void calculateHighestPerYear(Map<Integer, List<DatePricePair>> byYear) {
        System.out.println("Highest Per Year:");
        for (var entry : byYear.entrySet()) {
            List<DatePricePair> datePricePairs = entry.getValue();
            Optional<DatePricePair> highest = datePricePairs.stream().max(Comparator.comparing(DatePricePair::getPrice));
            highest.ifPresentOrElse(datePricePair -> {
                System.out.println(datePricePair.getDate().getYear() + " : " + String.format("%.2f", datePricePair.getPrice()));
            }, () -> {
                System.out.println("No prices available");
            });
        }
    }

    private void calculateLowestPerYear(Map<Integer, List<DatePricePair>> byYear) {
        System.out.println("Lowest Per Year:");
        for (var entry : byYear.entrySet()) {
            List<DatePricePair> datePricePairs = entry.getValue();
            Optional<DatePricePair> lowest = datePricePairs.stream().min(Comparator.comparing(DatePricePair::getPrice));
            lowest.ifPresentOrElse(datePricePair -> {
                System.out.println(datePricePair.getDate().getYear() + " : " + String.format("%.2f", datePricePair.getPrice()));
            }, () -> {
                System.out.println("No prices available");
            });
        }
    }

    // ideally, this would be in a separate file, but i dont want to change up my package structure right now...
    private static class DatePricePair {
        private final LocalDate date;
        private final double price;

        public DatePricePair(LocalDate date, double price) {
            this.date = date;
            this.price = price;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Date: " + this.getDate() + " Price: " + String.format("%.2f", this.getPrice());
        }
    }
}
