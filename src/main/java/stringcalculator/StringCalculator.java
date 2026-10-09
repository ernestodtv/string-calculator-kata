package stringcalculator;

import java.util.regex.Pattern;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";

    private static final String CUSTOM_DELIMITER_PREFIX = "//";

    private static final String NEW_LINE = "\n";

    public static int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        ParsedInput parsedInput = ParsedInput.from(input);
        return sum(parsedInput.numbers(), parsedInput.delimiterRegex());
    }

    private static int sum(String numbers, String delimiterRegex) {
        int total = 0;
        for (String number : numbers.split(delimiterRegex)) {
            int parsedNumber = Integer.parseInt(number);
            if (parsedNumber < 0) {
                throw new IllegalArgumentException("negatives not allowed: " + parsedNumber);
            }
            total += parsedNumber;
        }
        return total;
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

    }

}
