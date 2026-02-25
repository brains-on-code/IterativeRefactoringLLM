package com.thealgorithms.dynamicprogramming;

/**
 * @author Afrizal Fikri (https://github.com/icalF)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int findFirstGreaterOrEqual(int[] array, int left, int right, int target) {
        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (array[middle] >= target) {
                right = middle;
            } else {
                left = middle;
            }
        }
        return right;
    }

    public static int lis(int[] sequence) {
        int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }

        int[] subsequenceTails = new int[sequenceLength];

        int lisLength = 1;

        subsequenceTails[0] = sequence[0];
        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];

            if (currentValue < subsequenceTails[0]) {
                subsequenceTails[0] = currentValue;
            } else if (currentValue > subsequenceTails[lisLength - 1]) {
                subsequenceTails[lisLength++] = currentValue;
            } else {
                int position = findFirstGreaterOrEqual(subsequenceTails, -1, lisLength - 1, currentValue);
                subsequenceTails[position] = currentValue;
            }
        }

        return lisLength;
    }

    /**
     * @author Alon Firestein (https://github.com/alonfirestein)
     */
    // A function for finding the length of the LIS algorithm in O(nlogn) complexity.
    public static int findLISLen(int[] sequence) {
        final int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }
        int[] subsequenceTails = new int[sequenceLength];
        subsequenceTails[0] = sequence[0];
        int lisLength = 1;
        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];
            int position = findInsertionPosition(subsequenceTails, lisLength - 1, currentValue);
            subsequenceTails[position] = currentValue;
            if (position == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    // O(logn)
    private static int findInsertionPosition(int[] subsequenceTails, int lastIndex, int target) {
        int left = 0;
        int right = lastIndex;

        if (target < subsequenceTails[0]) {
            return 0;
        }
        if (target > subsequenceTails[lastIndex]) {
            return lastIndex + 1;
        }

        while (left < right - 1) {
            final int middle = (left + right) >>> 1;
            if (subsequenceTails[middle] < target) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return right;
    }
}