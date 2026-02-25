package com.thealgorithms.dynamicprogramming;

/**
 * @author Afrizal Fikri (https://github.com/icalF)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int findFirstGreaterOrEqual(int[] values, int leftIndex, int rightIndex, int target) {
        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (values[middleIndex] >= target) {
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

        int[] subsequenceTails = new int[sequenceLength];
        int lisLength = 1;

        subsequenceTails[0] = sequence[0];
        for (int sequenceIndex = 1; sequenceIndex < sequenceLength; sequenceIndex++) {
            int currentValue = sequence[sequenceIndex];

            if (currentValue < subsequenceTails[0]) {
                subsequenceTails[0] = currentValue;
            } else if (currentValue > subsequenceTails[lisLength - 1]) {
                subsequenceTails[lisLength++] = currentValue;
            } else {
                int insertionIndex =
                        findFirstGreaterOrEqual(subsequenceTails, -1, lisLength - 1, currentValue);
                subsequenceTails[insertionIndex] = currentValue;
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

        for (int sequenceIndex = 1; sequenceIndex < sequenceLength; sequenceIndex++) {
            int currentValue = sequence[sequenceIndex];
            int insertionIndex =
                    findInsertionPosition(subsequenceTails, lisLength - 1, currentValue);
            subsequenceTails[insertionIndex] = currentValue;
            if (insertionIndex == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    // O(logn)
    private static int findInsertionPosition(int[] subsequenceTails, int lastTailIndex, int target) {
        int leftIndex = 0;
        int rightIndex = lastTailIndex;

        if (target < subsequenceTails[0]) {
            return 0;
        }
        if (target > subsequenceTails[lastTailIndex]) {
            return lastTailIndex + 1;
        }

        while (leftIndex < rightIndex - 1) {
            final int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (subsequenceTails[middleIndex] < target) {
                leftIndex = middleIndex;
            } else {
                rightIndex = middleIndex;
            }
        }
        return rightIndex;
    }
}