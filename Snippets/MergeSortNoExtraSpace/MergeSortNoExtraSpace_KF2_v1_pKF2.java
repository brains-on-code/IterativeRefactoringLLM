package com.thealgorithms.sorts;

import java.util.Arrays;

public final class MergeSortNoExtraSpace {

    private MergeSortNoExtraSpace() {
        // Utility class; prevent instantiation
    }

    /**
     * Sorts the given array in ascending order using an in-place merge sort
     * that does not allocate extra arrays.
     *
     * <p>Note: This implementation only supports non-negative integers.
     *
     * @param array the array to sort
     * @return the same array instance, sorted
     * @throws IllegalArgumentException if the array contains negative numbers
     */
    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }

        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        // maxElement is used as a base to encode two values into one array cell:
        //   encodedValue = oldValue + (newValue % maxElement) * maxElement
        final int maxElement = Arrays.stream(array).max().getAsInt() + 1;

        mergeSort(array, 0, array.length - 1, maxElement);
        return array;
    }

    /**
     * Recursively sorts the subarray array[start..end] using merge sort.
     */
    public static void mergeSort(int[] array, int start, int end, int maxElement) {
        if (start >= end) {
            return;
        }

        final int middle = (start + end) >>> 1;
        mergeSort(array, start, middle, maxElement);
        mergeSort(array, middle + 1, end, maxElement);
        merge(array, start, middle, end, maxElement);
    }

    /**
     * Merges two consecutive sorted subarrays in-place:
     *   - Left:  array[start..middle]
     *   - Right: array[middle+1..end]
     *
     * The merge is done without extra space by encoding both old and new values
     * into each array cell using maxElement as a base.
     */
    private static void merge(int[] array, int start, int middle, int end, int maxElement) {
        int leftIndex = start;
        int rightIndex = middle + 1;
        int mergeIndex = start;

        // Merge while both halves have remaining elements
        while (leftIndex <= middle && rightIndex <= end) {
            int leftValue = array[leftIndex] % maxElement;
            int rightValue = array[rightIndex] % maxElement;

            if (leftValue <= rightValue) {
                array[mergeIndex] += leftValue * maxElement;
                leftIndex++;
            } else {
                array[mergeIndex] += rightValue * maxElement;
                rightIndex++;
            }
            mergeIndex++;
        }

        // Copy remaining elements from the left half, if any
        while (leftIndex <= middle) {
            int leftValue = array[leftIndex] % maxElement;
            array[mergeIndex] += leftValue * maxElement;
            leftIndex++;
            mergeIndex++;
        }

        // Copy remaining elements from the right half, if any
        while (rightIndex <= end) {
            int rightValue = array[rightIndex] % maxElement;
            array[mergeIndex] += rightValue * maxElement;
            rightIndex++;
            mergeIndex++;
        }

        // Decode the new sorted values by dividing by maxElement
        for (int i = start; i <= end; i++) {
            array[i] /= maxElement;
        }
    }
}