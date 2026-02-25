package com.thealgorithms.dynamicprogramming;

/**
 * @author Afrizal Fikri (https://github.com/icalF)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int findFirstGreaterOrEqual(int[] array, int left, int right, int target) {
        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (array[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public static int lis(int[] sequence) {
        int n = sequence.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        int length = 1;

        tails[0] = sequence[0];
        for (int i = 1; i < n; i++) {
            int value = sequence[i];

            if (value < tails[0]) {
                tails[0] = value;
            } else if (value > tails[length - 1]) {
                tails[length++] = value;
            } else {
                int pos = findFirstGreaterOrEqual(tails, -1, length - 1, value);
                tails[pos] = value;
            }
        }

        return length;
    }

    /**
     * @author Alon Firestein (https://github.com/alonfirestein)
     */
    // A function for finding the length of the LIS algorithm in O(nlogn) complexity.
    public static int findLISLen(int[] sequence) {
        final int n = sequence.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        tails[0] = sequence[0];
        int length = 1;

        for (int i = 1; i < n; i++) {
            int value = sequence[i];
            int pos = findInsertionPosition(tails, length - 1, value);
            tails[pos] = value;
            if (pos == length) {
                length++;
            }
        }
        return length;
    }

    // O(logn)
    private static int findInsertionPosition(int[] tails, int lastIndex, int target) {
        int left = 0;
        int right = lastIndex;

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[lastIndex]) {
            return lastIndex + 1;
        }

        while (left < right - 1) {
            final int mid = (left + right) >>> 1;
            if (tails[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }
}