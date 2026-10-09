package stringcalculator;

import java.util.regex.Pattern;

public class StringCalculator {

    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",";

        if (numbers.startsWith("//")) {
            String[] lines = numbers.split("\n", 2);
            delimiter = lines[0].substring(2);
            numbers = lines[1];
        }

        String regex = Pattern.quote(delimiter) + "|\n";

        int sum = 0;
        for (String number : numbers.split(regex)) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }

}
