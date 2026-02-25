package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {}

    private static int findUpperBoundIndex(int[] subsequenceTails, int leftIndex, int rightIndex, int targetValue) {
        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (subsequenceTails[middleIndex] >= targetValue) {
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
        int longestLength = 1;

        subsequenceTails[0] = sequence[0];
        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];

            if (currentValue < subsequenceTails[0]) {
                subsequenceTails[0] = currentValue;
            } else if (currentValue > subsequenceTails[longestLength - 1]) {
                subsequenceTails[longestLength++] = currentValue;
            } else {
                int insertionIndex = findUpperBoundIndex(subsequenceTails, -1, longestLength - 1, currentValue);
                subsequenceTails[insertionIndex] = currentValue;
            }
        }

        return longestLength;
    }

    public static int findLISLen(int[] sequence) {
        int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }

        int[] subsequenceTails = new int[sequenceLength];
        subsequenceTails[0] = sequence[0];
        int longestLength = 1;

        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];
            int insertionIndex = findInsertionIndex(subsequenceTails, longestLength - 1, currentValue);
            subsequenceTails[insertionIndex] = currentValue;
            if (insertionIndex == longestLength) {
                longestLength++;
            }
        }
        return longestLength;
    }

    private static int findInsertionIndex(int[] subsequenceTails, int lastTailIndex, int targetValue) {
        int leftIndex = 0;
        int rightIndex = lastTailIndex;

        if (targetValue < subsequenceTails[0]) {
            return 0;
        }
        if (targetValue > subsequenceTails[lastTailIndex]) {
            return lastTailIndex + 1;
        }

        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (subsequenceTails[middleIndex] < targetValue) {
                leftIndex = middleIndex;
            } else {
                rightIndex = middleIndex;
            }
        }
        return rightIndex;
    }
}