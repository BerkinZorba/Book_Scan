import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The BookScan class analyzes text and finds words of a specific length.
 * It also reports how many matching words were found and which line numbers
 * contain those words.
 */
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
     * Scans the given text and prints all words that match the target word length.
     * The method splits the text into lines, then scans each line word by word.
     *
     * This method uses substring, stringLength, and upperLowerCase internally.
     *
     * @param text       the text to scan
     * @param wordLength the target word length
     */
    public static void scanText(String text, int wordLength) {
        if (text == null) {
            System.out.println("Input text is null.");
            return;
        }

        if (text.isEmpty()) {
            System.out.println("Input text is empty.");
            return;
        }

        if (wordLength <= 0) {
            System.out.println("Word length must be greater than 0.");
            return;
        }

        List<String> matchingWords = new ArrayList<>();
        Set<Integer> lineNumbers = new LinkedHashSet<>();

        String[] lines = text.split("\\R");

        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
            String line = lines[lineIndex];

            int wordStart = -1;

            for (int i = 0; i <= line.length(); i++) {
                boolean endOfLine = i == line.length();
                boolean isLetterOrDigit = !endOfLine && Character.isLetterOrDigit(line.charAt(i));

                if (isLetterOrDigit && wordStart == -1) {
                    wordStart = i;
                }

                if ((!isLetterOrDigit || endOfLine) && wordStart != -1) {
                    String word = substring(line, wordStart, i);

                    String normalizedWord = upperLowerCase(word);

                    if (stringLength(normalizedWord) == wordLength) {
                        matchingWords.add(word);
                        lineNumbers.add(lineIndex + 1);
                    }

                    wordStart = -1;
                }
            }
        }

        System.out.println("Words of length " + wordLength + ": " + matchingWords);
        System.out.println("Count: " + matchingWords.size());

        if (lineNumbers.isEmpty()) {
            System.out.println("Found on line(s): none");
        } else {
            System.out.println("Found on line(s): " + lineNumbers);
        }
    }

    /**
     * Demonstrates the BookScan class by scanning sample text and showing
     * all required methods working together.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        String text = "The quick brown fox jumps over the lazy dog";

        System.out.println("Original text:");
        System.out.println(text);
        System.out.println();

        System.out.println("Demonstrating substring:");
        System.out.println("substring(\"The\", 0, 2): " + substring("The", 0, 2));
        System.out.println();

        System.out.println("Demonstrating stringLength:");
        System.out.println("stringLength(\"fox\"): " + stringLength("fox"));
        System.out.println();

        System.out.println("Demonstrating upperLowerCase:");
        System.out.println("upperLowerCase(\"The\"): " + upperLowerCase("The"));
        System.out.println();

        System.out.println("Scanning text:");
        scanText(text, 3);
    }
}
