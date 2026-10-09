package stringcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";

    private static final String CUSTOM_DELIMITER_PREFIX = "//";

    private static final String NEW_LINE = "\n";

    public static int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        ParsedInput parsedInput = ParsedInput.from(input);
        List<Integer> parsedNumbers = parsedInput.parseNumbers();
        ensureNoNegatives(parsedNumbers);
        return sum(parsedNumbers);
    }

    private static void ensureNoNegatives(List<Integer> numbers) {
        List<Integer> negativeNumbers = new ArrayList<>();
        for (int number : numbers) {
            if (number < 0) {
                negativeNumbers.add(number);
            }
        }
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("negatives not allowed: " + buildNegativesText(negativeNumbers));
        }
    }

    private static int sum(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

    private static String buildNegativesText(List<Integer> negativeNumbers) {
        return negativeNumbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private record ParsedInput(String delimiter, String numbers) {

        static ParsedInput from(String input) {
            if (!input.startsWith(CUSTOM_DELIMITER_PREFIX)) {
                return new ParsedInput(DEFAULT_DELIMITER, input);
            }
            String[] parts = input.split(NEW_LINE, 2);
            String header = parts[0];
            String numbers = parts[1];
            String delimiter = header.substring(CUSTOM_DELIMITER_PREFIX.length());
            return new ParsedInput(delimiter, numbers);
        }

        String delimiterRegex() {
            return Pattern.quote(delimiter) + "|" + NEW_LINE;
        }

        List<Integer> parseNumbers() {
            List<Integer> parsedNumbers = new ArrayList<>();
            for (String number : numbers.split(delimiterRegex())) {
                parsedNumbers.add(Integer.parseInt(number));
            }
            return parsedNumbers;
        }

    }

}
