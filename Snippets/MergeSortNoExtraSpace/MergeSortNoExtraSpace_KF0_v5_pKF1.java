package com.thealgorithms.sorts;

import java.util.Arrays;

/**
 * Implementation of Merge Sort without using extra space for merging.
 * This implementation performs in-place merging to sort the array of integers.
 */
public final class MergeSortNoExtraSpace {

    private MergeSortNoExtraSpace() {
    }

    /**
     * Sorts the array using in-place merge sort algorithm.
     *
     * @param array the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException If the array contains negative numbers.
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
     * Recursively divides the array into two halves, sorts and merges them.
     *
     * @param array        the array to be sorted
     * @param leftIndex    the starting index of the array
     * @param rightIndex   the ending index of the array
     * @param encodingBase the value greater than any element in the array, used for encoding
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
     * Merges two sorted subarrays [leftIndex...middleIndex] and [middleIndex+1...rightIndex] in place.
     *
     * @param array        the array containing the subarrays to be merged
     * @param leftIndex    the starting index of the first subarray
     * @param middleIndex  the ending index of the first subarray and starting index of the second subarray
     * @param rightIndex   the ending index of the second subarray
     * @param encodingBase the value greater than any element in the array, used for encoding
     */
    private static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex, int encodingBase) {
        int leftReadIndex = leftIndex;
        int rightReadIndex = middleIndex + 1;
        int writeIndex = leftIndex;

        while (leftReadIndex <= middleIndex && rightReadIndex <= rightIndex) {
            int leftValue = array[leftReadIndex] % encodingBase;
            int rightValue = array[rightReadIndex] % encodingBase;

            if (leftValue <= rightValue) {
                array[writeIndex] = array[writeIndex] + leftValue * encodingBase;
                leftReadIndex++;
            } else {
                array[writeIndex] = array[writeIndex] + rightValue * encodingBase;
                rightReadIndex++;
            }
            writeIndex++;
        }

        while (leftReadIndex <= middleIndex) {
            int leftValue = array[leftReadIndex] % encodingBase;
            array[writeIndex] = array[writeIndex] + leftValue * encodingBase;
            leftReadIndex++;
            writeIndex++;
        }

        while (rightReadIndex <= rightIndex) {
            int rightValue = array[rightReadIndex] % encodingBase;
            array[writeIndex] = array[writeIndex] + rightValue * encodingBase;
            rightReadIndex++;
            writeIndex++;
        }

        for (int index = leftIndex; index <= rightIndex; index++) {
            array[index] = array[index] / encodingBase;
        }
    }
}