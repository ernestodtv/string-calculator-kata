package stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";

    private static final String CUSTOM_DELIMITER_PREFIX = "//";

    private static final String DELIMITER_OPEN_BRACKET = "[";

    private static final String DELIMITER_CLOSE_BRACKET = "]";

    private static final String NEW_LINE = "\n";

    private static final int MAX_NUMBER = 1000;

    public static int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        ParsedInput parsedInput = ParsedInput.from(input);
        List<Integer> parsedNumbers = parsedInput.parseNumbers();
        ensureNoNegatives(parsedNumbers);
        return sum(ignoreNumbersAboveMax(parsedNumbers));
    }

    private static void ensureNoNegatives(List<Integer> numbers) {
        List<Integer> negativeNumbers = numbers.stream()
                .filter(number -> number < 0)
                .toList();
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("negatives not allowed: " + buildNegativesText(negativeNumbers));
        }
    }

    private static String buildNegativesText(List<Integer> negativeNumbers) {
        return negativeNumbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private static List<Integer> ignoreNumbersAboveMax(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number <= MAX_NUMBER)
                .toList();
    }

    private static int sum(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private record ParsedInput(String delimiter, String numbersText) {

        static ParsedInput from(String input) {
            if (!input.startsWith(CUSTOM_DELIMITER_PREFIX)) {
                return new ParsedInput(DEFAULT_DELIMITER, input);
            }
            String[] parts = input.split(NEW_LINE, 2);
            String header = parts[0];
            String numbersText = parts[1];
            String delimiterDefinition = header.substring(CUSTOM_DELIMITER_PREFIX.length());
            String delimiter = extractDelimiter(delimiterDefinition);
            return new ParsedInput(delimiter, numbersText);
        }

        private static String extractDelimiter(String delimiterDefinition) {
            if (delimiterDefinition.length() == 1) {
                return delimiterDefinition;
            }
            return delimiterDefinition.substring(
                    DELIMITER_OPEN_BRACKET.length(),
                    delimiterDefinition.indexOf(DELIMITER_CLOSE_BRACKET)
            );
        }

        private String delimiterRegex() {
            return Pattern.quote(delimiter) + "|" + NEW_LINE;
        }

        List<Integer> parseNumbers() {
            return Arrays.stream(numbersText.split(delimiterRegex()))
                    .map(Integer::parseInt)
                    .toList();
        }

    }

}