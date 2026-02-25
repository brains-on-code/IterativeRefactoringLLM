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
     * @param values the input array
     * @return an Optional containing the majority element if it exists, otherwise an empty Optional
     */
    public static Optional<Integer> findMajorityElement(int[] values) {
        if (values == null || values.length == 0) {
            return Optional.empty();
        }

        int majorityCandidate = findMajorityCandidate(values);
        int majorityCandidateFrequency = countOccurrences(majorityCandidate, values);

        if (isMajority(majorityCandidateFrequency, values.length)) {
            return Optional.of(majorityCandidate);
        }
        return Optional.empty();
    }

    /**
     * Identifies the potential majority candidate using Boyer-Moore's Voting Algorithm.
     *
     * @param values the input array
     * @return the candidate for the majority element
     */
    private static int findMajorityCandidate(final int[] values) {
        int voteCount = 0;
        int candidate = -1;

        for (int value : values) {
            if (voteCount == 0) {
                candidate = value;
            }
            voteCount += (value == candidate) ? 1 : -1;
        }
        return candidate;
    }

    /**
     * Counts the occurrences of the candidate element in the array.
     *
     * @param candidate the candidate element
     * @param values the input array
     * @return the number of times the candidate appears in the array
     */
    private static int countOccurrences(final int candidate, final int[] values) {
        int frequency = 0;
        for (int value : values) {
            if (value == candidate) {
                frequency++;
            }
        }
        return frequency;
    }

    /**
     * Determines if the count of the candidate element is more than n/2, where n is the length of the array.
     *
     * @param candidateFrequency the number of occurrences of the candidate
     * @param totalElementCount the total number of elements in the array
     * @return true if the candidate is the majority element, false otherwise
     */
    private static boolean isMajority(int candidateFrequency, int totalElementCount) {
        return 2 * candidateFrequency > totalElementCount;
    }
}