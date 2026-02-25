package com.thealgorithms.sorts;

import java.util.Arrays;

/**
 * In-place merge sort for non-negative integers.
 *
 * <p>This implementation avoids extra space for merging by encoding both
 * old and new values into each array element during the merge step.</p>
 */
public final class MergeSortNoExtraSpace {

    private MergeSortNoExtraSpace() {
        // Prevent instantiation
    }

    /**
     * Sorts the given array using an in-place merge sort algorithm.
     *
     * @param array the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException if the array contains negative numbers
     */
    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }

        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        // Base used for encoding: strictly greater than any element in the array
        final int maxElement = Arrays.stream(array).max().getAsInt() + 1;

        mergeSort(array, 0, array.length - 1, maxElement);
        return array;
    }

    /**
     * Recursively divides the array into halves, sorts each half, and merges them.
     *
     * @param array      the array to be sorted
     * @param start      the starting index of the current segment
     * @param end        the ending index of the current segment
     * @param maxElement a value greater than any element in the array, used for encoding
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
     * Merges two sorted subarrays [start, middle] and [middle + 1, end] in place.
     *
     * <p>Each element temporarily stores both its original value and the merged
     * value using the formula:
     *
     * <pre>
     *   array[index] = originalValue + (newValue % maxElement) * maxElement
     * </pre>
     *
     * After merging, a final pass decodes the new values by dividing by maxElement.</p>
     *
     * @param array      the array containing the subarrays to be merged
     * @param start      the starting index of the first subarray
     * @param middle     the ending index of the first subarray
     * @param end        the ending index of the second subarray
     * @param maxElement a value greater than any element in the array, used for encoding
     */
    private static void merge(int[] array, int start, int middle, int end, int maxElement) {
        int leftIndex = start;
        int rightIndex = middle + 1;
        int mergeIndex = start;

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

        while (leftIndex <= middle) {
            int leftValue = array[leftIndex] % maxElement;
            array[mergeIndex] += leftValue * maxElement;
            leftIndex++;
            mergeIndex++;
        }

        while (rightIndex <= end) {
            int rightValue = array[rightIndex] % maxElement;
            array[mergeIndex] += rightValue * maxElement;
            rightIndex++;
            mergeIndex++;
        }

        for (int i = start; i <= end; i++) {
            array[i] /= maxElement;
        }
    }
}