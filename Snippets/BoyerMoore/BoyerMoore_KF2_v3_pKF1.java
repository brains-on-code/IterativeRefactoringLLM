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
        int candidate = -1;
        int voteBalance = 0;

        for (int number : numbers) {
            if (voteBalance == 0) {
                candidate = number;
            }
            voteBalance += (number == candidate) ? 1 : -1;
        }

        return candidate;
    }

    private static int countOccurrences(final int target, final int[] numbers) {
        int count = 0;

        for (int number : numbers) {
            if (number == target) {
                count++;
            }
        }

        return count;
    }

    private static boolean isMajority(int count, int arrayLength) {
        return 2 * count > arrayLength;
    }
}