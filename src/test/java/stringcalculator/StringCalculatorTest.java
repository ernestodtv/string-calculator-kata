package stringcalculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringCalculatorTest {

    @Test
    void should_return_0_for_empty_string() {
        int sum = StringCalculator.add("");

        assertThat(sum).isZero();
    }

    @Test
    void should_return_1_for_1() {
        int sum = StringCalculator.add("1");

        assertThat(sum).isOne();
    }

}
