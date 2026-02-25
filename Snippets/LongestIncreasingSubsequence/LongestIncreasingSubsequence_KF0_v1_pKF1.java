package com.thealgorithms.dynamicprogramming;

/**
 * @author Afrizal Fikri (https://github.com/icalF)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int upperBound(int[] array, int left, int right, int key) {
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
        int inputLength = inputArray.length;
        if (inputLength == 0) {
            return 0;
        }

        int[] tailValues = new int[inputLength];

        // Always points to the empty slot in tailValues
        int lisLength = 1;

        tailValues[0] = inputArray[0];
        for (int i = 1; i < inputLength; i++) {
            int currentValue = inputArray[i];

            // New smallest value
            if (currentValue < tailValues[0]) {
                tailValues[0] = currentValue;
            }
            // currentValue extends largest subsequence
            else if (currentValue > tailValues[lisLength - 1]) {
                tailValues[lisLength++] = currentValue;
            }
            // currentValue will become end candidate of an existing subsequence or
            // throw away larger elements in all LIS, to make room for upcoming greater elements
            // than currentValue (and also, currentValue would have already appeared in one of LIS,
            // identify the location and replace it)
            else {
                int position = upperBound(tailValues, -1, lisLength - 1, currentValue);
                tailValues[position] = currentValue;
            }
        }

        return lisLength;
    }

    /**
     * @author Alon Firestein (https://github.com/alonfirestein)
     */
    // A function for finding the length of the LIS algorithm in O(nlogn) complexity.
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

    // O(logn)
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