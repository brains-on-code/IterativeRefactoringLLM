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

        int majorityCandidate = findMajorityCandidate(numbers);
        int majorityCandidateFrequency = countOccurrences(majorityCandidate, numbers);

        if (isMajority(majorityCandidateFrequency, numbers.length)) {
            return Optional.of(majorityCandidate);
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
        int currentFrequency = 0;
        int currentCandidate = -1;

        for (int number : numbers) {
            if (currentFrequency == 0) {
                currentCandidate = number;
            }
            currentFrequency += (number == currentCandidate) ? 1 : -1;
        }
        return currentCandidate;
    }

    /**
     * Counts how many times the candidate appears in the array.
     *
     * @param candidate the value to count
     * @param numbers   the input array
     * @return the number of occurrences of candidate
     */
    private static int countOccurrences(final int candidate, final int[] numbers) {
        int occurrenceCount = 0;
        for (int number : numbers) {
            if (number == candidate) {
                occurrenceCount++;
            }
        }
        return occurrenceCount;
    }

    /**
     * Checks if the count represents a majority in an array of given length.
     *
     * @param occurrenceCount the number of occurrences
     * @param arrayLength     the length of the array
     * @return true if occurrenceCount is more than half of arrayLength, false otherwise
     */
    private static boolean isMajority(int occurrenceCount, int arrayLength) {
        return 2 * occurrenceCount > arrayLength;
    }
}