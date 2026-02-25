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

        final int encodingBase = Arrays.stream(array).max().getAsInt() + 1;
        mergeSort(array, 0, array.length - 1, encodingBase);
        return array;
    }

    /**
     * Recursively sorts the subarray array[leftIndex..rightIndex] using merge sort.
     */
    public static void mergeSort(int[] array, int leftIndex, int rightIndex, int encodingBase) {
        if (leftIndex < rightIndex) {
            final int middleIndex = (leftIndex + rightIndex) >>> 1;
            mergeSort(array, leftIndex, middleIndex, encodingBase);
            mergeSort(array, middleIndex + 1, rightIndex, encodingBase);
            merge(array, leftIndex, middleIndex, rightIndex, encodingBase);
        }
    }

    /**
     * Merges two sorted subarrays array[leftIndex..middleIndex] and
     * array[middleIndex+1..rightIndex] into a single sorted segment.
     */
    private static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex, int encodingBase) {
        int leftSubarrayIndex = leftIndex;
        int rightSubarrayIndex = middleIndex + 1;
        int mergedArrayIndex = leftIndex;

        while (leftSubarrayIndex <= middleIndex && rightSubarrayIndex <= rightIndex) {
            int leftValue = array[leftSubarrayIndex] % encodingBase;
            int rightValue = array[rightSubarrayIndex] % encodingBase;

            if (leftValue <= rightValue) {
                array[mergedArrayIndex] += leftValue * encodingBase;
                mergedArrayIndex++;
                leftSubarrayIndex++;
            } else {
                array[mergedArrayIndex] += rightValue * encodingBase;
                mergedArrayIndex++;
                rightSubarrayIndex++;
            }
        }

        while (leftSubarrayIndex <= middleIndex) {
            int leftValue = array[leftSubarrayIndex] % encodingBase;
            array[mergedArrayIndex] += leftValue * encodingBase;
            mergedArrayIndex++;
            leftSubarrayIndex++;
        }

        while (rightSubarrayIndex <= rightIndex) {
            int rightValue = array[rightSubarrayIndex] % encodingBase;
            array[mergedArrayIndex] += rightValue * encodingBase;
            mergedArrayIndex++;
            rightSubarrayIndex++;
        }

        for (int index = leftIndex; index <= rightIndex; index++) {
            array[index] /= encodingBase;
        }
    }
}