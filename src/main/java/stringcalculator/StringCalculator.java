package stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";

    private static final String CUSTOM_DELIMITERS_PREFIX = "//";

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

    private record ParsedInput(List<String> delimiters, String numbersText) {

        static ParsedInput from(String input) {
            if (!input.startsWith(CUSTOM_DELIMITERS_PREFIX)) {
                return new ParsedInput(List.of(DEFAULT_DELIMITER), input);
            }
            String[] parts = input.split(NEW_LINE, 2);
            String header = parts[0];
            String numbersText = parts[1];
            String delimitersDefinition = header.substring(CUSTOM_DELIMITERS_PREFIX.length());
            List<String> delimiters = extractDelimiters(delimitersDefinition);
            return new ParsedInput(delimiters, numbersText);
        }

        private static List<String> extractDelimiters(String delimitersDefinition) {
            if (delimitersDefinition.length() == 1) {
                return List.of(delimitersDefinition);
            }
            if (!isBracketed(delimitersDefinition)) {
                throw new IllegalArgumentException("invalid delimiter definition: " + delimitersDefinition);
            }
            return List.of(
                    delimitersDefinition.substring(
                            DELIMITER_OPEN_BRACKET.length(),
                            delimitersDefinition.length() - DELIMITER_CLOSE_BRACKET.length()
                    )
            );
        }

        private static boolean isBracketed(String delimitersDefinition) {
            return delimitersDefinition.startsWith(DELIMITER_OPEN_BRACKET)
                    && delimitersDefinition.endsWith(DELIMITER_CLOSE_BRACKET)
                    && hasContentBetweenBrackets(delimitersDefinition);
        }

        private static boolean hasContentBetweenBrackets(String delimitersDefinition) {
            return delimitersDefinition.length()
                    > DELIMITER_OPEN_BRACKET.length() + DELIMITER_CLOSE_BRACKET.length();
        }

        private String delimiterRegex() {
            return delimiters.stream()
                    .map(Pattern::quote)
                    .collect(Collectors.joining("|"))
                    .concat("|" + NEW_LINE);
        }

        List<Integer> parseNumbers() {
            return Arrays.stream(numbersText.split(delimiterRegex()))
                    .map(Integer::parseInt)
                    .toList();
        }

    }

}