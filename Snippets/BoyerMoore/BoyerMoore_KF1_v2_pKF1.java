package com.thealgorithms.others;

import java.util.Optional;

/**
 * Utility class for finding a majority element in an array.
 */
public final class MajorityElementFinder {

    private MajorityElementFinder() {
    }

    /**
     * Finds the majority element in the given array, if it exists.
     * A majority element is an element that appears more than n/2 times.
     *
     * @param numbers the input array
     * @return an Optional containing the majority element if it exists, otherwise Optional.empty()
     */
    public static Optional<Integer> findMajorityElement(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Optional.empty();
        }

        int candidate = findMajorityCandidate(numbers);
        int candidateCount = countOccurrences(candidate, numbers);

        if (isMajority(candidateCount, numbers.length)) {
            return Optional.of(candidate);
        }
        return Optional.empty();
    }

    /**
     * Uses Boyer-Moore majority vote algorithm to find a candidate for majority element.
     *
     * @param numbers the input array
     * @return the candidate for majority element
     */
    private static int findMajorityCandidate(final int[] numbers) {
        int count = 0;
        int candidate = -1;

        for (int number : numbers) {
            if (count == 0) {
                candidate = number;
            }
            count += (number == candidate) ? 1 : -1;
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
        for (int number : numbers) {
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
    private static boolean isMajority(int count, int length) {
        return 2 * count > length;
    }
}