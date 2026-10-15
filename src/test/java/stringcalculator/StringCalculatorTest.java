package stringcalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void should_return_the_number_for_a_single_number(String input, int expected) {
        int sum = StringCalculator.add(input);

        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2'      , 3",
            "'2,5,7'    , 14",
            "'20,30,50' , 100",
            "'1,2,3,4,5', 15"
    })
    void should_return_the_sum_for_two_or_more_numbers(String input, int expected) {
        int sum = StringCalculator.add(input);

        assertThat(sum).isEqualTo(expected);
    }

    @Test
    void should_accept_new_lines_between_numbers() {
        int sum = StringCalculator.add("1\n2,3");

        assertThat(sum).isEqualTo(6);
    }

    @ParameterizedTest
    @CsvSource({
            "'//;\n1;2',    3",
            "'//.\n2.6.1',  9",
            "'//^\n1^2\n4', 7",
            "'//[\n1[2',    3"
    })
    void should_accept_custom_delimiters(String input, int expected) {
        int sum = StringCalculator.add(input);

        assertThat(sum).isEqualTo(expected);
    }

    @Test
    void should_throw_exception_for_a_negative_number() {
        assertThatThrownBy(() -> StringCalculator.add("-1,2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("negatives not allowed: -1");
    }

    @Test
    void should_throw_exception_for_several_negative_numbers() {
        assertThatThrownBy(() -> StringCalculator.add("-1,2,3,-4,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("negatives not allowed: -1, -4");
    }

    @ParameterizedTest
    @CsvSource({
            "'2,1001', 2",
            "'2,1000', 1002"
    })
    void should_ignore_numbers_bigger_than_1000(String input, int expected) {
        int sum = StringCalculator.add(input);

        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'//[***]\n1***2***3', 6",
            "'//[..]\n1..2..3\n4', 10",
            "'//[^^^]\n2^^^2^^^2^^^2', 8"
    })
    void should_accept_delimiters_of_any_length_between_square_brackets(String input, int expected) {
        int sum = StringCalculator.add(input);

        assertThat(sum).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'//[***\n1***2', '[***'",
            "'//[]\n12',      '[]'",
            "'//abc\n1abc2',  'abc'"
    })
    void should_throw_exception_for_invalid_delimiter_definition(String input, String invalidDefinition) {
        assertThatThrownBy(() -> StringCalculator.add(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("invalid delimiter definition: " + invalidDefinition);
    }

}
