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
        int leftPointer = leftIndex;
        int rightPointer = middleIndex + 1;
        int mergePointer = leftIndex;

        while (leftPointer <= middleIndex && rightPointer <= rightIndex) {
            int leftValue = array[leftPointer] % encodingBase;
            int rightValue = array[rightPointer] % encodingBase;

            if (leftValue <= rightValue) {
                array[mergePointer] = array[mergePointer] + leftValue * encodingBase;
                leftPointer++;
            } else {
                array[mergePointer] = array[mergePointer] + rightValue * encodingBase;
                rightPointer++;
            }
            mergePointer++;
        }

        while (leftPointer <= middleIndex) {
            int leftValue = array[leftPointer] % encodingBase;
            array[mergePointer] = array[mergePointer] + leftValue * encodingBase;
            leftPointer++;
            mergePointer++;
        }

        while (rightPointer <= rightIndex) {
            int rightValue = array[rightPointer] % encodingBase;
            array[mergePointer] = array[mergePointer] + rightValue * encodingBase;
            rightPointer++;
            mergePointer++;
        }

        for (int index = leftIndex; index <= rightIndex; index++) {
            array[index] = array[index] / encodingBase;
        }
    }
}