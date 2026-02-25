package com.thealgorithms.others;
import java.util.Optional;


public final class BoyerMoore {
    private BoyerMoore() {
    }


    public static Optional<Integer> findMajorityElement(int[] array) {
        if (array == null || array.length == 0) {
            return Optional.empty();
        }

        int candidate = findCandidate(array);
        int count = countOccurrences(candidate, array);

        if (isMajority(count, array.length)) {
            return Optional.of(candidate);
        }
        return Optional.empty();
    }


    private static int findCandidate(final int[] array) {
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


    private static int countOccurrences(final int candidate, final int[] array) {
        int count = 0;
        for (int value : array) {
            if (value == candidate) {
                count++;
            }
        }
        return count;
    }


    private static boolean isMajority(int count, int totalCount) {
        return 2 * count > totalCount;
    }
}
