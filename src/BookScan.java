import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class BookScan {

    /**
     * Extracts a portion of the given text from start index to end index.
     *
     * @param text  the text to extract from
     * @param start the starting index, inclusive
     * @param end   the ending index, exclusive
     * @return the extracted substring, or an empty string if input is invalid
     */
    public static String substring(String text, int start, int end) {
        if (text == null) {
            return "";
        }

        if (start < 0 || end < 0 || start > end || start >= text.length()) {
            return "";
        }

        if (end > text.length()) {
            end = text.length();
        }

        StringBuilder result = new StringBuilder();

        for (int i = start; i < end; i++) {
            result.append(text.charAt(i));
        }

        return result.toString();
    }

    /**
     * Returns the length of the given word.
     *
     * @param word the word whose length should be calculated
     * @return the length of the word, or 0 if the word is null
     */
    public static int stringLength(String word) {
        if (word == null) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < word.length(); i++) {
            count++;
        }

        return count;
    }

    /**
     * Converts the given word to lowercase so that scanning is case-insensitive.
     *
     * @param word the word to convert
     * @return the lowercase version of the word, or an empty string if null
     */
    public static String upperLowerCase(String word) {
        if (word == null) {
            return "";
        }

        return word.toLowerCase();
    }

    /**
     * Scans the given text and prints every word matching the target word length.
     *
     * @param text       the text to scan
     * @param wordLength the target word length
     */
    public static void scanText(String text, int wordLength) {
        if (text == null || text.isEmpty()) {
            System.out.println("No text was provided.");
            return;
        }

        if (wordLength <= 0) {
            System.out.println("Word length must be greater than 0.");
            return;
        }

        int totalMatches = 0;
        String[] lines = text.split("\\R", -1);

        System.out.println();
        System.out.println("Target word length searched: " + wordLength);
        System.out.println("Matching words:");

        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
            String line = lines[lineIndex];
            int wordStart = -1;

            for (int i = 0; i <= line.length(); i++) {
                boolean endOfLine = i == line.length();
                boolean isWordCharacter = !endOfLine && Character.isLetterOrDigit(line.charAt(i));

                if (isWordCharacter && wordStart == -1) {
                    wordStart = i;
                }

                if ((!isWordCharacter || endOfLine) && wordStart != -1) {
                    String word = substring(line, wordStart, i);
                    String normalizedWord = upperLowerCase(word);

                    if (stringLength(normalizedWord) == wordLength) {
                        totalMatches++;
                        System.out.println("Line " + (lineIndex + 1) + ": " + word);
                    }

                    wordStart = -1;
                }
            }
        }

        if (totalMatches == 0) {
            System.out.println("No matching words found.");
        }

        System.out.println("Total count: " + totalMatches);
    }

    /**
     * Runs the BookScan program.
     *
     * @param args optional command-line arguments; the first argument may be a file path
     */
    public static void main(String[] args) {
        String text;

        try {
            text = loadText(args);
        } catch (IOException e) {
            System.out.println("Could not read input: " + e.getMessage());
            return;
        }

        int wordLength = readWordLength();
        scanText(text, wordLength);
    }

    private static String loadText(String[] args) throws IOException {
        if (args != null && args.length > 0) {
            return Files.readString(Path.of(args[0]), StandardCharsets.UTF_8);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        if (System.console() == null) {
            StringBuilder pipedInput = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                pipedInput.append(line).append(System.lineSeparator());
            }

            return pipedInput.toString();
        }

        System.out.println("Enter or paste text below. Submit a blank line when finished:");

        StringBuilder typedInput = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            typedInput.append(line).append(System.lineSeparator());
        }

        return typedInput.toString();
    }

    private static int readWordLength() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter target word length: ");

            if (scanner.hasNextInt()) {
                int wordLength = scanner.nextInt();

                if (wordLength > 0) {
                    return wordLength;
                }
            } else {
                scanner.next();
            }

            System.out.println("Please enter a positive integer.");
        }
    }
}