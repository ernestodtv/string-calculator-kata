import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SanityTest {

    @Test
    void should_run_tests_with_junit_and_assertj() {
        assertThat(1 + 1).isEqualTo(2);
    }

}
