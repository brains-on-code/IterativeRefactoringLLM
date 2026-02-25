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
        int leftSubarrayIndex = leftIndex;
        int rightSubarrayIndex = middleIndex + 1;
        int mergedArrayIndex = leftIndex;

        while (leftSubarrayIndex <= middleIndex && rightSubarrayIndex <= rightIndex) {
            int leftElement = array[leftSubarrayIndex] % encodingBase;
            int rightElement = array[rightSubarrayIndex] % encodingBase;

            if (leftElement <= rightElement) {
                array[mergedArrayIndex] = array[mergedArrayIndex] + leftElement * encodingBase;
                leftSubarrayIndex++;
            } else {
                array[mergedArrayIndex] = array[mergedArrayIndex] + rightElement * encodingBase;
                rightSubarrayIndex++;
            }
            mergedArrayIndex++;
        }

        while (leftSubarrayIndex <= middleIndex) {
            int leftElement = array[leftSubarrayIndex] % encodingBase;
            array[mergedArrayIndex] = array[mergedArrayIndex] + leftElement * encodingBase;
            leftSubarrayIndex++;
            mergedArrayIndex++;
        }

        while (rightSubarrayIndex <= rightIndex) {
            int rightElement = array[rightSubarrayIndex] % encodingBase;
            array[mergedArrayIndex] = array[mergedArrayIndex] + rightElement * encodingBase;
            rightSubarrayIndex++;
            mergedArrayIndex++;
        }

        for (int currentIndex = leftIndex; currentIndex <= rightIndex; currentIndex++) {
            array[currentIndex] = array[currentIndex] / encodingBase;
        }
    }
}