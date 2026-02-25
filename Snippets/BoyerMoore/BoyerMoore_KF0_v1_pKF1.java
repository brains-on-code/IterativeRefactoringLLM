package com.thealgorithms.others;

import java.util.Optional;

/**
 * Utility class implementing Boyer-Moore's Voting Algorithm to find the majority element
 * in an array. The majority element is defined as the element that appears more than n/2 times
 * in the array, where n is the length of the array.
 *
 * For more information on the algorithm, refer to:
 * https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm
 */
public final class BoyerMoore {

    private BoyerMoore() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the majority element in the given array if it exists.
     *
     * @param numbers the input array
     * @return an Optional containing the majority element if it exists, otherwise an empty Optional
     */
    public static Optional<Integer> findMajorityElement(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Optional.empty();
        }

        int majorityCandidate = findMajorityCandidate(numbers);
        int majorityCandidateCount = countOccurrences(majorityCandidate, numbers);

        if (isMajority(majorityCandidateCount, numbers.length)) {
            return Optional.of(majorityCandidate);
        }
        return Optional.empty();
    }

    /**
     * Identifies the potential majority candidate using Boyer-Moore's Voting Algorithm.
     *
     * @param numbers the input array
     * @return the candidate for the majority element
     */
    private static int findMajorityCandidate(final int[] numbers) {
        int voteBalance = 0;
        int currentCandidate = -1;

        for (int number : numbers) {
            if (voteBalance == 0) {
                currentCandidate = number;
            }
            voteBalance += (number == currentCandidate) ? 1 : -1;
        }
        return currentCandidate;
    }

    /**
     * Counts the occurrences of the candidate element in the array.
     *
     * @param candidate the candidate element
     * @param numbers the input array
     * @return the number of times the candidate appears in the array
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
     * Determines if the count of the candidate element is more than n/2, where n is the length of the array.
     *
     * @param candidateCount the number of occurrences of the candidate
     * @param totalElementCount the total number of elements in the array
     * @return true if the candidate is the majority element, false otherwise
     */
    private static boolean isMajority(int candidateCount, int totalElementCount) {
        return 2 * candidateCount > totalElementCount;
    }
}