package com.thealgorithms.sorts;

import java.util.Arrays;

/**
 * In-place merge sort implementation for non-negative integers.
 *
 * <p>This implementation uses an encoding trick to merge subarrays in-place
 * without additional memory. It relies on the fact that all numbers are
 * non-negative and uses the maximum value in the array to encode both old
 * and new values in the same array cell during merging.</p>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Sorts the given array of non-negative integers in ascending order using
     * an in-place merge sort algorithm.
     *
     * @param array the array to sort; must not contain negative numbers
     * @return the sorted array (same reference as the input)
     * @throws IllegalArgumentException if the array contains negative numbers
     */
    public static int[] method1(int[] array) {
        if (array.length == 0) {
            return array;
        }

        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        // 'base' is one greater than the maximum element; used for encoding.
        final int base = Arrays.stream(array).max().getAsInt() + 1;
        method2(array, 0, array.length - 1, base);
        return array;
    }

    /**
     * Recursively sorts the subarray array[left..right] using merge sort.
     *
     * @param array the array to sort
     * @param left  the left index of the subarray
     * @param right the right index of the subarray
     * @param base  encoding base (greater than any element in the array)
     */
    public static void method2(int[] array, int left, int right, int base) {
        if (left < right) {
            final int mid = (left + right) >>> 1;
            method2(array, left, mid, base);
            method2(array, mid + 1, right, base);
            method3(array, left, mid, right, base);
        }
    }

    /**
     * Merges two sorted subarrays of {@code array} in-place:
     * <ul>
     *   <li>First subarray:  array[left..mid]</li>
     *   <li>Second subarray: array[mid+1..right]</li>
     * </ul>
     *
     * <p>The method encodes the merged values into the existing array cells by
     * storing: {@code newValue = oldValue + (sortedValue % base) * base}.
     * After merging, a final pass divides each element by {@code base} to
     * retrieve the sorted values.</p>
     *
     * @param array the array containing the subarrays to merge
     * @param left  left index of the first subarray
     * @param mid   right index of the first subarray (midpoint)
     * @param right right index of the second subarray
     * @param base  encoding base (greater than any element in the array)
     */
    private static void method3(int[] array, int left, int mid, int right, int base) {
        int leftIndex = left;
        int rightIndex = mid + 1;
        int mergeIndex = left;

        // Merge while both subarrays have elements.
        while (leftIndex <= mid && rightIndex <= right) {
            if (array[leftIndex] % base <= array[rightIndex] % base) {
                array[mergeIndex] = array[mergeIndex] + (array[leftIndex] % base) * base;
                mergeIndex++;
                leftIndex++;
            } else {
                array[mergeIndex] = array[mergeIndex] + (array[rightIndex] % base) * base;
                mergeIndex++;
                rightIndex++;
            }
        }

        // Copy remaining elements from the left subarray, if any.
        while (leftIndex <= mid) {
            array[mergeIndex] = array[mergeIndex] + (array[leftIndex] % base) * base;
            mergeIndex++;
            leftIndex++;
        }

        // Copy remaining elements from the right subarray, if any.
        while (rightIndex <= right) {
            array[mergeIndex] = array[mergeIndex] + (array[rightIndex] % base) * base;
            mergeIndex++;
            rightIndex++;
        }

        // Decode: retrieve the sorted values by dividing by 'base'.
        for (int i = left; i <= right; i++) {
            array[i] = array[i] / base;
        }
    }
}