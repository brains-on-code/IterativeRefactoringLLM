package com.thealgorithms.others;

import java.util.Optional;

public final class BoyerMoore {

    private BoyerMoore() {
    }

    public static Optional<Integer> findMajorityElement(int[] array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }

        int majorityCandidate = findMajorityCandidate(array);
        int majorityCandidateFrequency = countFrequency(majorityCandidate, array);

        if (isMajority(majorityCandidateFrequency, array.length)) {
            return Optional.of(majorityCandidate);
        }
        return Optional.empty();
    }

    private static int findMajorityCandidate(final int[] array) {
        int candidate = -1;
        int candidateVoteBalance = 0;

        for (int value : array) {
            if (candidateVoteBalance == 0) {
                candidate = value;
            }
            candidateVoteBalance += (value == candidate) ? 1 : -1;
        }

        return candidate;
    }

    private static int countFrequency(final int targetValue, final int[] array) {
        int frequency = 0;

        for (int value : array) {
            if (value == targetValue) {
                frequency++;
            }
        }

        return frequency;
    }

    private static boolean isMajority(int frequency, int arrayLength) {
        return 2 * frequency > arrayLength;
    }
}