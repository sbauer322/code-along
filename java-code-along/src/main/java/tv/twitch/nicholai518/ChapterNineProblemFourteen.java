package tv.twitch.nicholai518;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// word separator problem
// input: StopAndSmellTheRoses.
// output: Stop and smell the roses.
public class ChapterNineProblemFourteen {
    public void run() {
        var filepath= "src/main/resources/tv/twitch/nicholai518/ch9prb14.txt";
        try {
            Files.lines(Path.of(filepath)).forEach((line) -> System.out.println(splitSentence(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String splitSentence(String sentence) {
        var accumulator = new StringBuilder();
        for (char c : sentence.toCharArray()) {
            if (Character.isUpperCase(c) && accumulator.length() == 0) {
                accumulator.append(Character.toUpperCase(c));
            } else if (Character.isUpperCase(c) && accumulator.length() > 0) {
                accumulator.append(" ").append(Character.toLowerCase(c));
            } else {
                accumulator.append(c);
            }
        }
        return accumulator.toString();
    }
}
