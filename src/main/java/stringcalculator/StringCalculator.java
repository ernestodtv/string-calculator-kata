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

        String delimiter = DEFAULT_DELIMITER;

        if (hasCustomDelimiter(input)) {
            String[] parts = input.split(NEW_LINE, 2);

            delimiter = parts[0].substring(CUSTOM_DELIMITER_PREFIX.length());
            input = parts[1];
        }

        String delimiterRegex = Pattern.quote(delimiter) + "|" + NEW_LINE;

        return sum(input, delimiterRegex);
    }

    private static boolean hasCustomDelimiter(String input) {
        return input.startsWith(CUSTOM_DELIMITER_PREFIX);
    }

    private static int sum(String input, String regex) {
        int sum = 0;
        for (String number : input.split(regex)) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }

}
