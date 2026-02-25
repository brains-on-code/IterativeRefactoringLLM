package com.thealgorithms.others;

import java.util.Optional;

public final class BoyerMoore {

    private BoyerMoore() {
        // Utility class; prevent instantiation
    }

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

    private static int countOccurrences(int candidate, int[] array) {
        int count = 0;

        for (int value : array) {
            if (value == candidate) {
                count++;
            }
        }

        return count;
    }

    private static boolean isMajority(int count, int totalCount) {
        return count * 2 > totalCount;
    }
}