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
        int majorityCandidateCount = countOccurrences(majorityCandidate, numbers);

        if (isMajority(majorityCandidateCount, numbers.length)) {
            return Optional.of(majorityCandidate);
        }
        return Optional.empty();
    }

    private static int findMajorityCandidate(final int[] numbers) {
        int currentCandidate = -1;
        int currentVoteCount = 0;

        for (int number : numbers) {
            if (currentVoteCount == 0) {
                currentCandidate = number;
            }
            currentVoteCount += (number == currentCandidate) ? 1 : -1;
        }

        return currentCandidate;
    }

    private static int countOccurrences(final int targetNumber, final int[] numbers) {
        int occurrenceCount = 0;

        for (int number : numbers) {
            if (number == targetNumber) {
                occurrenceCount++;
            }
        }

        return occurrenceCount;
    }

    private static boolean isMajority(int occurrenceCount, int totalCount) {
        return 2 * occurrenceCount > totalCount;
    }
}