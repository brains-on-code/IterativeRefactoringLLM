package com.thealgorithms.sorts;

import java.util.Arrays;

/**
 * Sorts an array of non-negative integers using a merge-sort-based approach.
 */
public final class NonNegativeMergeSort {

    private NonNegativeMergeSort() {
    }

    /**
     * Sorts the given array in ascending order.
     *
     * @param array the array to sort; must contain no negative numbers
     * @return the sorted array (same instance as input)
     * @throws IllegalArgumentException if the array contains negative numbers
     */
    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        final int base = Arrays.stream(array).max().getAsInt() + 1;
        mergeSort(array, 0, array.length - 1, base);
        return array;
    }

    /**
     * Recursively sorts the subarray array[leftIndex..rightIndex] using merge sort.
     */
    public static void mergeSort(int[] array, int leftIndex, int rightIndex, int base) {
        if (leftIndex < rightIndex) {
            final int middleIndex = (leftIndex + rightIndex) >>> 1;
            mergeSort(array, leftIndex, middleIndex, base);
            mergeSort(array, middleIndex + 1, rightIndex, base);
            merge(array, leftIndex, middleIndex, rightIndex, base);
        }
    }

    /**
     * Merges two sorted subarrays array[leftIndex..middleIndex] and
     * array[middleIndex+1..rightIndex] into a single sorted segment.
     */
    private static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex, int base) {
        int leftPointer = leftIndex;
        int rightPointer = middleIndex + 1;
        int mergeIndex = leftIndex;

        while (leftPointer <= middleIndex && rightPointer <= rightIndex) {
            if (array[leftPointer] % base <= array[rightPointer] % base) {
                array[mergeIndex] = array[mergeIndex] + (array[leftPointer] % base) * base;
                mergeIndex++;
                leftPointer++;
            } else {
                array[mergeIndex] = array[mergeIndex] + (array[rightPointer] % base) * base;
                mergeIndex++;
                rightPointer++;
            }
        }

        while (leftPointer <= middleIndex) {
            array[mergeIndex] = array[mergeIndex] + (array[leftPointer] % base) * base;
            mergeIndex++;
            leftPointer++;
        }

        while (rightPointer <= rightIndex) {
            array[mergeIndex] = array[mergeIndex] + (array[rightPointer] % base) * base;
            mergeIndex++;
            rightPointer++;
        }

        for (int index = leftIndex; index <= rightIndex; index++) {
            array[index] = array[index] / base;
        }
    }
}