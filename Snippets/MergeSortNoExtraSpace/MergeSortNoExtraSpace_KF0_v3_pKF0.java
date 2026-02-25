package com.thealgorithms.sorts;

import java.util.Arrays;

/**
 * Implementation of Merge Sort without using extra space for merging.
 * This implementation performs in-place merging to sort the array of integers.
 */
public final class MergeSortNoExtraSpace {

    private MergeSortNoExtraSpace() {
        // Utility class; prevent instantiation
    }

    /**
     * Sorts the array using in-place merge sort algorithm.
     *
     * @param array the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException If the array contains negative numbers.
     */
    public static int[] sort(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        validateNoNegativeNumbers(array);

        final int maxElement = findMaxElement(array) + 1;
        mergeSort(array, 0, array.length - 1, maxElement);
        return array;
    }

    private static void validateNoNegativeNumbers(int[] array) {
        boolean hasNegative = Arrays.stream(array).anyMatch(value -> value < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }
    }

    private static int findMaxElement(int[] array) {
        return Arrays.stream(array).max().orElseThrow();
    }

    /**
     * Recursively divides the array into two halves, sorts and merges them.
     *
     * @param array      the array to be sorted
     * @param start      the starting index of the array
     * @param end        the ending index of the array
     * @param maxElement the value greater than any element in the array, used for encoding
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
     * Merges two sorted subarrays [start...middle] and [middle+1...end] in place.
     *
     * @param array      the array containing the subarrays to be merged
     * @param start      the starting index of the first subarray
     * @param middle     the ending index of the first subarray and starting index of the second subarray
     * @param end        the ending index of the second subarray
     * @param maxElement the value greater than any element in the array, used for encoding
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

        decodeMergedSegment(array, start, end, maxElement);
    }

    private static void decodeMergedSegment(int[] array, int start, int end, int maxElement) {
        for (int i = start; i <= end; i++) {
            array[i] /= maxElement;
        }
    }
}