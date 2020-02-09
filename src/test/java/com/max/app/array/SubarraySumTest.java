package com.max.app.array;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SubarraySumTest {

    @Test
    public void findSubarraySumNormalCase() {
        int[] arr = {10, 7, 5, 3, 0, 2, 1, 6, 1, 4, 133};

        assertBoundary(SubarraySum.findSubarraySum(arr, 11), 2, 6);
        assertBoundary(SubarraySum.findSubarraySum(arr, 8), 2, 3);

        // check array prefix
        assertBoundary(SubarraySum.findSubarraySum(arr, 10), 0, 0);
        assertBoundary(SubarraySum.findSubarraySum(arr, 17), 0, 1);
        assertBoundary(SubarraySum.findSubarraySum(arr, 25), 0, 3);

        // check array suffix
        assertBoundary(SubarraySum.findSubarraySum(arr, 133), 10, 10);
        assertBoundary(SubarraySum.findSubarraySum(arr, 137), 9, 10);
        assertBoundary(SubarraySum.findSubarraySum(arr, 138), 8, 10);
    }

    @Test
    public void findSubarraySumWithIntegerMax() {
        assertBoundary(SubarraySum.findSubarraySum(new int[]{0, 1, Integer.MAX_VALUE, 5}, Integer.MAX_VALUE), 2, 2);
    }

    @Test
    public void findSubarraySumWithSumOverflow() {
        int[] arr = {Integer.MAX_VALUE, 1, Integer.MAX_VALUE, 2, 5, Integer.MAX_VALUE};

        assertBoundary(SubarraySum.findSubarraySum(arr, 1), 1, 1);
        assertBoundary(SubarraySum.findSubarraySum(arr, 2), 3, 3);
        assertBoundary(SubarraySum.findSubarraySum(arr, 7), 3, 4);

        assertNotFound(SubarraySum.findSubarraySum(arr, 8));
        assertNotFound(SubarraySum.findSubarraySum(arr, 3));
        assertNotFound(SubarraySum.findSubarraySum(arr, 9));
    }

    @Test
    public void findSubarraySumOneElementArray() {
        assertBoundary(SubarraySum.findSubarraySum(new int[]{10}, 10), 0, 0);
        assertBoundary(SubarraySum.findSubarraySum(new int[]{0}, 0), 0, 0);

        assertNotFound(SubarraySum.findSubarraySum(new int[]{13}, 5));
        assertNotFound(SubarraySum.findSubarraySum(new int[]{13}, 77));
    }

    @Test
    public void findSubarraySumNotFound() {
        assertNotFound(SubarraySum.findSubarraySum(new int[]{10, 7, 5, 3, 0, 2, 1, 6, 1, 4, 8}, 177));
    }

    @Test
    public void findSubarraySumZeroValue() {
        int[] arr = {10, 7, 5, 3, 0, 2, 1, 6, 1, 4, 133};
        assertBoundary(SubarraySum.findSubarraySum(arr, 0), 4, 4);
    }

    @Test
    public void findSubarraySumZeroValueNotFound() {
        int[] arr = {10, 7, 5, 3, 4, 2, 1, 6, 1, 4, 133};
        assertNotFound(SubarraySum.findSubarraySum(arr, 0));
    }

    @Test
    public void findSubarrayFailWithNulArray() {
        assertThatThrownBy(() -> SubarraySum.findSubarraySum(null, 177)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("null 'arr' parameter passed");
    }

    @Test
    public void findSubarrayFailNegativeExpectedSum() {
        assertThatThrownBy(() -> SubarraySum.findSubarraySum(new int[]{10, 20, 30}, -3)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("negative expected sum detected: -3, should be positive or 0 value");
    }

    @Test
    public void findSubarraySumWithNegativeArrayElementsShouldFail() {
        int[] arr = {10, 7, 5, 3, 0, 2, -1, 6, 1, 4, 133};

        assertThatThrownBy(() -> SubarraySum.findSubarraySum(arr, 777)).
                isInstanceOf(IllegalStateException.class).
                hasMessage("Negative array value detected at index: 6");
    }

    private static void assertNotFound(Optional<SubarraySum.Boundary> res) {
        assertThat(res).isEmpty();
    }

    private static void assertBoundary(Optional<SubarraySum.Boundary> res, int from, int to) {
        assertThat(res).isNotEmpty();
        SubarraySum.Boundary boundary = res.get();

        assertThat(boundary.from).
                as("boundary 'from' is incorrect, expected %s, found: %s", from, boundary.from).
                isEqualTo(from);

        assertThat(boundary.to).
                as("boundary 'to' is incorrect, expected %s, found: %s", to, boundary.to).
                isEqualTo(to);
    }
}
