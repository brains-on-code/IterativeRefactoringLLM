package com.thealgorithms.others;

import java.util.Optional;

/**
 * Boyer–Moore majority vote algorithm.
 *
 * <p>Finds an element that appears more than n/2 times in an array, if it exists.</p>
 */
public final class BoyerMoore {

    private BoyerMoore() {
        // Prevent instantiation
    }

    /**
     * Finds the majority element in the given array, if it exists.
     * A majority element appears more than n/2 times.
     *
     * @param array the input array of integers
     * @return an {@link Optional} containing the majority element, or {@link Optional#empty()} if none exists
     */
    public static Optional<Integer> findMajorityElement(int[] array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }

        int candidate = findCandidate(array);
        int occurrences = countOccurrences(candidate, array);

        return isMajority(occurrences, array.length)
            ? Optional.of(candidate)
            : Optional.empty();
    }

    /**
     * Runs the Boyer–Moore voting procedure to obtain a majority candidate.
     * The returned candidate must be verified separately.
     *
     * @param array the input array of integers
     * @return the candidate for majority element
     */
    private static int findCandidate(int[] array) {
        int count = 0;
        int candidate = -1;

        for (int value : array) {
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
     * @param array     the input array of integers
     * @return the number of occurrences of the candidate
     */
    private static int countOccurrences(int candidate, int[] array) {
        int count = 0;

        for (int value : array) {
            if (value == candidate) {
                count++;
            }
        }

        return count;
    }

    /**
     * Checks whether the given count qualifies as a majority.
     *
     * @param count      the number of occurrences of the element
     * @param totalCount the total number of elements in the array
     * @return {@code true} if the element is a majority element, {@code false} otherwise
     */
    private static boolean isMajority(int count, int totalCount) {
        return count * 2 > totalCount;
    }
}