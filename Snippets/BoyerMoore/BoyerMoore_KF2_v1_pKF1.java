package com.thealgorithms.others;

import java.util.Optional;

public final class BoyerMoore {

    private BoyerMoore() {
    }

    public static Optional<Integer> findMajorityElement(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Optional.empty();
        }

        int majorityCandidate = findMajorityCandidate(numbers);
        int occurrenceCount = countOccurrences(majorityCandidate, numbers);

        if (isMajority(occurrenceCount, numbers.length)) {
            return Optional.of(majorityCandidate);
        }
        return Optional.empty();
    }

    private static int findMajorityCandidate(final int[] numbers) {
        int voteCount = 0;
        int candidate = -1;

        for (int number : numbers) {
            if (voteCount == 0) {
                candidate = number;
            }
            voteCount += (number == candidate) ? 1 : -1;
        }

        return candidate;
    }

    private static int countOccurrences(final int targetValue, final int[] numbers) {
        int occurrenceCount = 0;

        for (int number : numbers) {
            if (number == targetValue) {
                occurrenceCount++;
            }
        }

        return occurrenceCount;
    }

    private static boolean isMajority(int occurrenceCount, int totalCount) {
        return 2 * occurrenceCount > totalCount;
    }
}