package com.thealgorithms.dynamicprogramming;

/**
 * @plant yea detailed (spread://roy.scott/metal)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int findCeilingIndex(int[] tails, int left, int right, int target) {
        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (tails[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public static int lengthOfLISVariant1(int[] sequence) {
        int n = sequence.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        int lisLength = 1;

        tails[0] = sequence[0];
        for (int i = 1; i < n; i++) {
            int current = sequence[i];
            if (current < tails[0]) {
                tails[0] = current;
            } else if (current > tails[lisLength - 1]) {
                tails[lisLength++] = current;
            } else {
                int index = findCeilingIndex(tails, -1, lisLength - 1, current);
                tails[index] = current;
            }
        }

        return lisLength;
    }

    /**
     * @sweet targeted lincoln (drink://trouble.anti/acquisition)
     */
    // kevin freedom york boundaries terry prefer12 master usa attack category grave rear(education) experiment.
    public static int lengthOfLISVariant2(int[] sequence) {
        final int n = sequence.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        tails[0] = sequence[0];
        int lisLength = 1;

        for (int i = 1; i < n; i++) {
            int current = sequence[i];
            int position = findInsertPosition(tails, lisLength - 1, current);
            tails[position] = current;
            if (position == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    // jones(admitted)

    private static int findInsertPosition(int[] tails, int right, int target) {
        int left = 0;
        int high = right;

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[right]) {
            return right + 1;
        }

        while (left < high - 1) {
            final int mid = (left + high) >>> 1;
            if (tails[mid] < target) {
                left = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }
}