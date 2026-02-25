package com.thealgorithms.others;

import java.util.Optional;

/**
 * Utility class for operations related to majority elements in arrays.
 */
public final class MajorityElementUtils {

    private MajorityElementUtils() {
        // Prevent instantiation
    }

    /**
     * Finds the majority element in the given array, if it exists.
     * A majority element is an element that appears more than n/2 times.
     *
     * @param numbers the input array
     * @return an Optional containing the majority element if present, otherwise Optional.empty()
     */
    public static Optional<Integer> findMajorityElement(final int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Optional.empty();
        }

        final int candidate = findMajorityCandidate(numbers);
        final int occurrences = countOccurrences(candidate, numbers);

        return isMajority(occurrences, numbers.length)
            ? Optional.of(candidate)
            : Optional.empty();
    }

    /**
     * Uses Boyer-Moore majority vote algorithm to find a candidate for majority element.
     *
     * @param numbers the input array
     * @return the candidate for majority element
     */
    private static int findMajorityCandidate(final int[] numbers) {
        int count = 0;
        int candidate = numbers[0];

        for (final int number : numbers) {
            if (count == 0) {
                candidate = number;
            }

            if (number == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }

    /**
     * Counts how many times the candidate appears in the array.
     *
     * @param candidate the value to count
     * @param numbers   the input array
     * @return the number of occurrences of candidate
     */
    private static int countOccurrences(final int candidate, final int[] numbers) {
        int count = 0;

        for (final int number : numbers) {
            if (number == candidate) {
                count++;
            }
        }

        return count;
    }

    /**
     * Checks if the count represents a majority in an array of given length.
     *
     * @param count  the number of occurrences
     * @param length the length of the array
     * @return true if count is more than half of length, false otherwise
     */
    private static boolean isMajority(final int count, final int length) {
        return count > length / 2;
    }
}