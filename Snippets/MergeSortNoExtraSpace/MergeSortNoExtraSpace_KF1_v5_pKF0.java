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

        validateNonNegative(array);

        final int encodingBase = findMaxValue(array) + 1;
        sort(array, 0, array.length - 1, encodingBase);
        return array;
    }

    private static void validateNonNegative(int[] array) {
        boolean hasNegative = Arrays.stream(array).anyMatch(value -> value < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }
    }

    private static int findMaxValue(int[] array) {
        return Arrays.stream(array).max().orElseThrow();
    }

    /**
     * Recursively sorts the subarray array[left..right] using merge sort.
     *
     * @param array        the array to sort
     * @param left         left index of the subarray
     * @param right        right index of the subarray
     * @param encodingBase encoding base (max element + 1)
     */
    public static void sort(int[] array, int left, int right, int encodingBase) {
        if (left >= right) {
            return;
        }

        final int mid = (left + right) >>> 1;
        sort(array, left, mid, encodingBase);
        sort(array, mid + 1, right, encodingBase);
        mergeInPlace(array, left, mid, right, encodingBase);
    }

    /**
     * Merges two sorted subarrays array[left..mid] and array[mid+1..right] in-place
     * using encoding with base encodingBase.
     *
     * @param array        the array containing the subarrays to merge
     * @param left         left index of the first subarray
     * @param mid          right index of the first subarray
     * @param right        right index of the second subarray
     * @param encodingBase encoding base (max element + 1)
     */
    private static void mergeInPlace(int[] array, int left, int mid, int right, int encodingBase) {
        int leftIndex = left;
        int rightIndex = mid + 1;
        int mergeIndex = left;

        while (leftIndex <= mid && rightIndex <= right) {
            int leftValue = getOriginalValue(array[leftIndex], encodingBase);
            int rightValue = getOriginalValue(array[rightIndex], encodingBase);

            if (leftValue <= rightValue) {
                encodeValue(array, mergeIndex, leftValue, encodingBase);
                leftIndex++;
            } else {
                encodeValue(array, mergeIndex, rightValue, encodingBase);
                rightIndex++;
            }
            mergeIndex++;
        }

        while (leftIndex <= mid) {
            int leftValue = getOriginalValue(array[leftIndex], encodingBase);
            encodeValue(array, mergeIndex, leftValue, encodingBase);
            leftIndex++;
            mergeIndex++;
        }

        while (rightIndex <= right) {
            int rightValue = getOriginalValue(array[rightIndex], encodingBase);
            encodeValue(array, mergeIndex, rightValue, encodingBase);
            rightIndex++;
            mergeIndex++;
        }

        decodeRange(array, left, right, encodingBase);
    }

    private static int getOriginalValue(int encodedValue, int encodingBase) {
        return encodedValue % encodingBase;
    }

    private static void encodeValue(int[] array, int index, int value, int encodingBase) {
        array[index] += value * encodingBase;
    }

    private static void decodeRange(int[] array, int left, int right, int encodingBase) {
        for (int i = left; i <= right; i++) {
            array[i] /= encodingBase;
        }
    }
}