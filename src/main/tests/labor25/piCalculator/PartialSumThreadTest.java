package labor25.piCalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("CallToThreadRun")
class PartialSumThreadTest {

    @Test
    void constructor_negativeMinimum_exception() {
        assertThatThrownBy(() -> new PartialSumThread(-1, 1));
    }

    @Test
    void constructor_maxSmallerMin_getSumReturns0() {
        PartialSumThread partialSumThread = new PartialSumThread(1, 0);

        partialSumThread.run();

        assertThat(partialSumThread.getSum()).isZero();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 1",
            "0, 1, 0.666666666666666",
            "0, 2, 0.866666666666666",
            "2, 2, 0.2",
            "2, 3, 0.057142857142857",
            "47, 1931, -0.00544794686752715",
            "0, 100000, 0.78540066337243"
    })
    void run_validValues_getSumReturnsResult(int min, int max, double expected) {
        PartialSumThread partialSumThread = new PartialSumThread(min, max);

        partialSumThread.run();

        assertThat(partialSumThread.getSum()).isCloseTo(expected, within(1e-15));
    }
}