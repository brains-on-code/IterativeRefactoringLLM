package com.thealgorithms.others;

import java.util.Optional;

/**
 * Boyer–Moore majority vote algorithm.
 *
 * Finds an element that appears more than n/2 times in an array (if it exists).
 * See: https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm
 */
public final class BoyerMoore {

    private BoyerMoore() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the majority element in the array, if one exists.
     *
     * @param array input array
     * @return Optional containing the majority element, or empty if none exists
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
     * Phase 1: find a majority candidate.
     *
     * @param array input array
     * @return candidate value
     */
    private static int findCandidate(final int[] array) {
        int candidate = array[0];
        int count = 1;

        for (int i = 1; i < array.length; i++) {
            int value = array[i];

            if (value == candidate) {
                count++;
            } else if (count == 0) {
                candidate = value;
                count = 1;
            } else {
                count--;
            }
        }

        return candidate;
    }

    /**
     * Phase 2: count how many times the candidate appears.
     *
     * @param candidate value to count
     * @param array     input array
     * @return number of occurrences of candidate
     */
    private static int countOccurrences(final int candidate, final int[] array) {
        int count = 0;
        for (int value : array) {
            if (value == candidate) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the candidate is a strict majority (> n/2).
     *
     * @param count      occurrences of candidate
     * @param totalCount array length
     * @return true if candidate is majority, false otherwise
     */
    private static boolean isMajority(int count, int totalCount) {
        return count > totalCount / 2;
    }
}