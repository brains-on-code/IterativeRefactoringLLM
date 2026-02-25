package com.thealgorithms.others;

import java.util.Optional;

/**
 * Utility class implementing the Boyer-Moore majority vote algorithm.
 * Provides a method to find an element that appears more than n/2 times in an array.
 */
public final class BoyerMoore {

    private BoyerMoore() {
        // Prevent instantiation
    }

    /**
     * Finds the majority element in the given array, if it exists.
     * A majority element is an element that appears more than n/2 times.
     *
     * @param array the input array of integers
     * @return an Optional containing the majority element if present, otherwise Optional.empty()
     */
    public static Optional<Integer> findMajorityElement(int[] array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }

        int candidate = findCandidate(array);
        int count = countOccurrences(candidate, array);

        return isMajority(count, array.length) ? Optional.of(candidate) : Optional.empty();
    }

    /**
     * Uses the Boyer-Moore voting algorithm to find a candidate for majority element.
     * This candidate must be verified separately.
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
     * Counts how many times the candidate appears in the array.
     *
     * @param candidate the value to count
     * @param array     the input array of integers
     * @return the number of occurrences of candidate in array
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
     * Checks whether the count of an element qualifies it as a majority element.
     *
     * @param count      the number of occurrences of the element
     * @param totalCount the total number of elements in the array
     * @return true if the element is a majority element, false otherwise
     */
    private static boolean isMajority(int count, int totalCount) {
        return count * 2 > totalCount;
    }
}