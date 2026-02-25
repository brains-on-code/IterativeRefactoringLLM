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
     * Recursively sorts the subarray array[left..right] using merge sort.
     */
    public static void mergeSort(int[] array, int left, int right, int base) {
        if (left < right) {
            final int mid = (left + right) >>> 1;
            mergeSort(array, left, mid, base);
            mergeSort(array, mid + 1, right, base);
            merge(array, left, mid, right, base);
        }
    }

    /**
     * Merges two sorted subarrays array[left..mid] and
     * array[mid+1..right] into a single sorted segment.
     */
    private static void merge(int[] array, int left, int mid, int right, int base) {
        int leftIndex = left;
        int rightIndex = mid + 1;
        int mergedIndex = left;

        while (leftIndex <= mid && rightIndex <= right) {
            int leftValue = array[leftIndex] % base;
            int rightValue = array[rightIndex] % base;

            if (leftValue <= rightValue) {
                array[mergedIndex] += leftValue * base;
                mergedIndex++;
                leftIndex++;
            } else {
                array[mergedIndex] += rightValue * base;
                mergedIndex++;
                rightIndex++;
            }
        }

        while (leftIndex <= mid) {
            int leftValue = array[leftIndex] % base;
            array[mergedIndex] += leftValue * base;
            mergedIndex++;
            leftIndex++;
        }

        while (rightIndex <= right) {
            int rightValue = array[rightIndex] % base;
            array[mergedIndex] += rightValue * base;
            mergedIndex++;
            rightIndex++;
        }

        for (int index = left; index <= right; index++) {
            array[index] /= base;
        }
    }
}