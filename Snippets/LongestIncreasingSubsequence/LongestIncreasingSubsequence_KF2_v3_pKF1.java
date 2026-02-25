package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {}

    private static int findUpperBoundIndex(int[] tailValues, int leftIndex, int rightIndex, int targetValue) {
        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (tailValues[middleIndex] >= targetValue) {
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

        int[] tailValues = new int[sequenceLength];
        int lisLength = 1;

        tailValues[0] = sequence[0];
        for (int i = 1; i < sequenceLength; i++) {
            int currentValue = sequence[i];

            if (currentValue < tailValues[0]) {
                tailValues[0] = currentValue;
            } else if (currentValue > tailValues[lisLength - 1]) {
                tailValues[lisLength++] = currentValue;
            } else {
                int insertPosition = findUpperBoundIndex(tailValues, -1, lisLength - 1, currentValue);
                tailValues[insertPosition] = currentValue;
            }
        }

        return lisLength;
    }

    public static int findLISLen(int[] sequence) {
        int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }

        int[] tailValues = new int[sequenceLength];
        tailValues[0] = sequence[0];
        int lisLength = 1;

        for (int i = 1; i < sequenceLength; i++) {
            int currentValue = sequence[i];
            int insertPosition = findInsertionIndex(tailValues, lisLength - 1, currentValue);
            tailValues[insertPosition] = currentValue;
            if (insertPosition == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    private static int findInsertionIndex(int[] tailValues, int lastTailIndex, int targetValue) {
        int leftIndex = 0;
        int rightIndex = lastTailIndex;

        if (targetValue < tailValues[0]) {
            return 0;
        }
        if (targetValue > tailValues[lastTailIndex]) {
            return lastTailIndex + 1;
        }

        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (tailValues[middleIndex] < targetValue) {
                leftIndex = middleIndex;
            } else {
                rightIndex = middleIndex;
            }
        }
        return rightIndex;
    }
}