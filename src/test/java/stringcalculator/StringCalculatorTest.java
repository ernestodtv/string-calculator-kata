package stringcalculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringCalculatorTest {

    @Test
    void should_return_0_for_empty_string() {
        int sum = StringCalculator.add("");

        assertThat(sum).isZero();
    }

}
