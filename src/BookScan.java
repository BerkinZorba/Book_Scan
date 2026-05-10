import java.util.ArrayList;
import java.util.List;
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

        List<String> matchingWords = new ArrayList<>();
        String[] lines = text.split("\\R", -1);

        for (String line : lines) {
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
                        matchingWords.add(word);
                    }

                    wordStart = -1;
                }
            }
        }
        System.out.println("\nTarget word length searched: " + wordLength);
        System.out.println("Matching words: " + String.join(", ", matchingWords));
        System.out.println("Total count: " + matchingWords.size());
    }

    /**
     * Runs the BookScan program.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Paste or type your text, then press Enter:");
        String text = scanner.nextLine();

        int wordLength = readWordLength(scanner);
        scanText(text, wordLength);
    }

    private static int readWordLength(Scanner scanner) {
        while (true) {
            System.out.print("\nEnter target word length: ");

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