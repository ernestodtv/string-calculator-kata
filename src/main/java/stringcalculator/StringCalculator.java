package stringcalculator;

public class StringCalculator {

    public static int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (String number: numbers.split(",")) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }

}
