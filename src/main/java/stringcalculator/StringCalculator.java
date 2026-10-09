package stringcalculator;

import java.util.regex.Pattern;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = ",";

    private static final String CUSTOM_DELIMITER_PREFIX = "//";

    public static int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        String delimiter = DEFAULT_DELIMITER;

        if (hasCustomDelimiter(input)) {
            String[] lines = input.split("\n", 2);
            delimiter = lines[0].substring(CUSTOM_DELIMITER_PREFIX.length());
            input = lines[1];
        }

        String delimiterRegex = Pattern.quote(delimiter) + "|\n";

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
