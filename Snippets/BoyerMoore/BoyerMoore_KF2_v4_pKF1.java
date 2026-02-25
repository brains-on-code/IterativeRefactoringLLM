package com.thealgorithms.others;

import java.util.Optional;

public final class BoyerMoore {

    private BoyerMoore() {
    }

    public static Optional<Integer> findMajorityElement(int[] array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }

        int candidate = findMajorityCandidate(array);
        int candidateFrequency = countOccurrences(candidate, array);

        if (isMajority(candidateFrequency, array.length)) {
            return Optional.of(candidate);
        }
        return Optional.empty();
    }

    private static int findMajorityCandidate(final int[] array) {
        int candidate = -1;
        int voteCount = 0;

        for (int value : array) {
            if (voteCount == 0) {
                candidate = value;
            }
            voteCount += (value == candidate) ? 1 : -1;
        }

        return candidate;
    }

    private static int countOccurrences(final int targetValue, final int[] array) {
        int occurrenceCount = 0;

        for (int value : array) {
            if (value == targetValue) {
                occurrenceCount++;
            }
        }

        return occurrenceCount;
    }

    private static boolean isMajority(int occurrenceCount, int arrayLength) {
        return 2 * occurrenceCount > arrayLength;
    }
}