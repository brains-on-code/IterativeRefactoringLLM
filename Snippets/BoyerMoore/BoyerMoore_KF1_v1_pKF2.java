package com.thealgorithms.others;

import java.util.Optional;

/**
 * Utility class for finding the majority element in an array.
 *
 * A majority element is an element that appears more than n/2 times
 * in the given array of size n.
 *
 * This implementation uses the Boyer–Moore majority vote algorithm.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the majority element in the given array, if it exists.
     *
     * @param numbers the input array of integers
     * @return an {@link Optional} containing the majority element if present,
     *         or {@link Optional#empty()} if no majority element exists
     */
    public static Optional<Integer> method1(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Optional.empty();
        }

        int candidate = method2(numbers);
        int count = method3(candidate, numbers);

        if (method4(count, numbers.length)) {
            return Optional.of(candidate);
        }
        return Optional.empty();
    }

    /**
     * Finds a candidate for the majority element using the
     * Boyer–Moore majority vote algorithm.
     *
     * @param numbers the input array of integers
     * @return the candidate majority element
     */
    private static int method2(final int[] numbers) {
        int count = 0;
        int candidate = -1;

        for (int value : numbers) {
            if (count == 0) {
                candidate = value;
            }
            count += (value == candidate) ? 1 : -1;
        }
        return candidate;
    }

    /**
     * Counts how many times the given candidate appears in the array.
     *
     * @param candidate the value to count
     * @param numbers   the input array of integers
     * @return the number of occurrences of {@code candidate} in {@code numbers}
     */
    private static int method3(final int candidate, final int[] numbers) {
        int count = 0;
        for (int value : numbers) {
            if (value == candidate) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks whether the candidate's count is greater than half of the array size.
     *
     * @param count     the number of occurrences of the candidate
     * @param arraySize the size of the array
     * @return {@code true} if the candidate is a majority element, {@code false} otherwise
     */
    private static boolean method4(int count, int arraySize) {
        return 2 * count > arraySize;
    }
}