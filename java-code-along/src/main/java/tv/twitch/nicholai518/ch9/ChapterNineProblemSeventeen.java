package tv.twitch.nicholai518.ch9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lottery Statistics
 * Normal numbers range 1 - 69
 * Powerball numbers range 1 - 26
 *
 * Outputs:
 * Display 10 most common numbers and 10 least common numbers ordered by frequency
 * (skipped) Display the 10 most overdue numbers, ordered most overdue to least
 * (skipped) Display the frequency of each normal number and each powerball number
 */
public class ChapterNineProblemSeventeen {
    public void run() {
        var filepath = "src/main/resources/tv/twitch/nicholai518/ch9prb17.txt";
        var allNumbers = new HashMap<String, Integer>();
        try {
            Files.lines(Path.of(filepath)).forEach((line) -> {
                var values = line.split(" ");
                for (var v : values) {
                    var count = allNumbers.getOrDefault(v, 0);
                    allNumbers.put(v, count + 1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputStats(allNumbers);
    }

    public void outputStats(Map<String, Integer> numbers) {
        var high = calculateHighFrequency(numbers, 10);
        var low = calculateLowFrequency(numbers, 10);
        System.out.println("Most common numbers by frequency: " + high.toString());
        System.out.println("Least common numbers by frequency: " + low.toString());
    }

    // Assumes n is less than the length of the set of entries
    public List<String> calculateHighFrequency(Map<String, Integer> numbers, int n) {
        var sortedNumbers = numbers.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        Collections.reverse(sortedNumbers);

        return sortedNumbers.subList(0, n);
    }

    // Assumes n is less than the length of the set of entries
    public List<String> calculateLowFrequency(Map<String, Integer> numbers, int n) {
        return numbers.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .limit(n)
                .collect(Collectors.toList());
    }
}
