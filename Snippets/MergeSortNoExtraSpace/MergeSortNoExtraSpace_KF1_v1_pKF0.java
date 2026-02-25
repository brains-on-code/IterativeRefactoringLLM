package com.thealgorithms.sorts;

import java.util.Arrays;

/**
 * In-place merge sort implementation that does not support negative numbers.
 */
public final class Class1 {

    private Class1() {
        // Utility class
    }

    /**
     * Sorts the given array in ascending order using an in-place merge sort
     * variant that encodes values during merging.
     *
     * @param array the array to sort
     * @return the sorted array (same reference as input)
     * @throws IllegalArgumentException if the array contains negative numbers
     */
    public static int[] method1(int[] array) {
        if (array.length == 0) {
            return array;
        }

        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        final int maxValuePlusOne = Arrays.stream(array).max().getAsInt() + 1;
        method2(array, 0, array.length - 1, maxValuePlusOne);
        return array;
    }

    /**
     * Recursively sorts the subarray array[left..right] using merge sort.
     *
     * @param array          the array to sort
     * @param left           left index of the subarray
     * @param right          right index of the subarray
     * @param maxValuePlusOne encoding base (max element + 1)
     */
    public static void method2(int[] array, int left, int right, int maxValuePlusOne) {
        if (left < right) {
            final int mid = (left + right) >>> 1;
            method2(array, left, mid, maxValuePlusOne);
            method2(array, mid + 1, right, maxValuePlusOne);
            method3(array, left, mid, right, maxValuePlusOne);
        }
    }

    /**
     * Merges two sorted subarrays array[left..mid] and array[mid+1..right] in-place
     * using encoding with base maxValuePlusOne.
     *
     * @param array          the array containing the subarrays to merge
     * @param left           left index of the first subarray
     * @param mid            right index of the first subarray
     * @param right          right index of the second subarray
     * @param maxValuePlusOne encoding base (max element + 1)
     */
    private static void method3(int[] array, int left, int mid, int right, int maxValuePlusOne) {
        int leftIndex = left;
        int rightIndex = mid + 1;
        int mergeIndex = left;

        while (leftIndex <= mid && rightIndex <= right) {
            int leftValue = array[leftIndex] % maxValuePlusOne;
            int rightValue = array[rightIndex] % maxValuePlusOne;

            if (leftValue <= rightValue) {
                array[mergeIndex] += leftValue * maxValuePlusOne;
                leftIndex++;
            } else {
                array[mergeIndex] += rightValue * maxValuePlusOne;
                rightIndex++;
            }
            mergeIndex++;
        }

        while (leftIndex <= mid) {
            int leftValue = array[leftIndex] % maxValuePlusOne;
            array[mergeIndex] += leftValue * maxValuePlusOne;
            leftIndex++;
            mergeIndex++;
        }

        while (rightIndex <= right) {
            int rightValue = array[rightIndex] % maxValuePlusOne;
            array[mergeIndex] += rightValue * maxValuePlusOne;
            rightIndex++;
            mergeIndex++;
        }

        for (int i = left; i <= right; i++) {
            array[i] /= maxValuePlusOne;
        }
    }
}