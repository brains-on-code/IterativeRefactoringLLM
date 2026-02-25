package com.thealgorithms.dynamicprogramming;

/**
 * @author Afrizal Fikri (https://github.com/icalF)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int findFirstGreaterOrEqual(int[] values, int leftIndex, int rightIndex, int targetValue) {
        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (values[middleIndex] >= targetValue) {
                rightIndex = middleIndex;
            } else {
                leftIndex = middleIndex;
            }
        }
        return rightIndex;
    }

    public static int lis(int[] sequence) {
        int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }

        int[] tails = new int[sequenceLength];
        int lisLength = 1;

        tails[0] = sequence[0];
        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];

            if (currentValue < tails[0]) {
                tails[0] = currentValue;
            } else if (currentValue > tails[lisLength - 1]) {
                tails[lisLength++] = currentValue;
            } else {
                int insertionIndex = findFirstGreaterOrEqual(tails, -1, lisLength - 1, currentValue);
                tails[insertionIndex] = currentValue;
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

        int[] tails = new int[sequenceLength];
        tails[0] = sequence[0];
        int lisLength = 1;

        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];
            int insertionIndex = findInsertionPosition(tails, lisLength - 1, currentValue);
            tails[insertionIndex] = currentValue;
            if (insertionIndex == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    // O(logn)
    private static int findInsertionPosition(int[] tails, int lastIndex, int targetValue) {
        int leftIndex = 0;
        int rightIndex = lastIndex;

        if (targetValue < tails[0]) {
            return 0;
        }
        if (targetValue > tails[lastIndex]) {
            return lastIndex + 1;
        }

        while (leftIndex < rightIndex - 1) {
            final int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (tails[middleIndex] < targetValue) {
                leftIndex = middleIndex;
            } else {
                rightIndex = middleIndex;
            }
        }
        return rightIndex;
    }
}