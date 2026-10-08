package stringcalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringCalculatorTest {

    @Test
    void should_return_0_for_empty_string() {
        int sum = StringCalculator.add("");

        assertThat(sum).isZero();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, 3",
            "12, 12"
    })
    void should_return_the_number_for_a_single_number(String numbers, int expected) {
        int sum = StringCalculator.add(numbers);

        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2'      , 3",
            "'2,5,7'    , 14",
            "'20,30,50' , 100"
    })
    void should_return_the_sum_for_two_or_more_numbers(String numbers, int expected) {
        int sum = StringCalculator.add(numbers);

        assertThat(sum).isEqualTo(expected);
    }

}
