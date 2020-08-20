package tv.twitch.nicholai518.ch9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ChapterNineProblemEleven {
    public void run() {
        var f = new File("src/main/resources/tv/twitch/nicholai518/ch9prb11.txt");
        var weeks = buildDataset(f);

        // highest sales, lowest sales, overall total sales
        int highestWeekIndex = 0;
        int smallestWeekIndex = 0;
        double total = 0.0;
        for (int i = 0; i < weeks.size(); i++) {
            double[] week = weeks.get(i);
            if (weeks.get(highestWeekIndex)[0] < week[0]) {
                highestWeekIndex = i;
            }

            if (weeks.get(smallestWeekIndex)[0] > week[0]) {
                smallestWeekIndex = i;
            }

            total += week[0];
        }

        log("Highest sales - week " + highestWeekIndex + " with $" + weeks.get(highestWeekIndex)[0]);
        log("Lowest sales - week " + smallestWeekIndex + " with $" + weeks.get(smallestWeekIndex)[0]);
        log("Total average overall sales $" + average(total, 3));
    }

    private ArrayList<double[]> buildDataset(File f) {
        var weeks = new ArrayList<double[]>();

        try (Scanner scanner = new Scanner(f)){
            while (scanner.hasNextLine()) {
                var nums = Arrays.stream(scanner.nextLine().split(",")).mapToDouble(Double::parseDouble).toArray();
                var weeklyTotal = total(nums);
                var weeklyAvg = average(weeklyTotal, nums.length);
                weeks.add(new double[]{weeklyTotal, weeklyAvg});
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return weeks;
    }

    private double total(double[] arr) {
        var sum = 0.0;
        for (double x : arr) {
            sum += x;
        }
        return sum;
    }

    private double average(double sum, int n) {
        return sum / n;
    }

    private void log(String s) {
        System.out.println(s);
    }
}

