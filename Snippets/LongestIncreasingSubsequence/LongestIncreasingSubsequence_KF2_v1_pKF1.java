package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {}

    private static int findUpperBoundIndex(int[] array, int left, int right, int key) {
        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (array[middle] >= key) {
                right = middle;
            } else {
                left = middle;
            }
        }
        return right;
    }

    public static int lis(int[] inputArray) {
        int arrayLength = inputArray.length;
        if (arrayLength == 0) {
            return 0;
        }

        int[] tailValues = new int[arrayLength];
        int lisLength = 1;

        tailValues[0] = inputArray[0];
        for (int i = 1; i < arrayLength; i++) {
            int currentValue = inputArray[i];

            if (currentValue < tailValues[0]) {
                tailValues[0] = currentValue;
            } else if (currentValue > tailValues[lisLength - 1]) {
                tailValues[lisLength++] = currentValue;
            } else {
                int position = findUpperBoundIndex(tailValues, -1, lisLength - 1, currentValue);
                tailValues[position] = currentValue;
            }
        }

        return lisLength;
    }

    public static int findLISLen(int[] inputArray) {
        final int size = inputArray.length;
        if (size == 0) {
            return 0;
        }

        int[] tailValues = new int[size];
        tailValues[0] = inputArray[0];
        int lisLength = 1;

        for (int i = 1; i < size; i++) {
            int currentValue = inputArray[i];
            int position = binarySearchBetween(tailValues, lisLength - 1, currentValue);
            tailValues[position] = currentValue;
            if (position == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    private static int binarySearchBetween(int[] tailValues, int endIndex, int key) {
        int left = 0;
        int right = endIndex;

        if (key < tailValues[0]) {
            return 0;
        }
        if (key > tailValues[endIndex]) {
            return endIndex + 1;
        }

        while (left < right - 1) {
            final int middle = (left + right) >>> 1;
            if (tailValues[middle] < key) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return right;
    }
}