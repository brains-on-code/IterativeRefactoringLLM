package com.thealgorithms.others;

/**
 * Provides a method to perform a right rotation on an array.
 * A right rotation operation shifts each element of the array
 * by a specified number of positions to the right.
 *
 * https://en.wikipedia.org/wiki/Right_rotation
 */
public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs an in-place right rotation on the given array by the specified number of positions.
     *
     * @param array the array to be rotated (must not be null or empty)
     * @param positions the number of positions to rotate the array to the right (must be non-negative)
     * @return the same array instance, rotated to the right
     * @throws IllegalArgumentException if the input array is null, empty, or positions is negative
     */
    public static int[] rotateRight(int[] array, int positions) {
        if (array == null || array.length == 0 || positions < 0) {
            throw new IllegalArgumentException("Array must be non-null, non-empty, and positions must be non-negative.");
        }

        int length = array.length;
        int effectivePositions = positions % length;

        if (effectivePositions == 0) {
            return array; // No rotation needed
        }

        reverse(array, 0, length - 1);
        reverse(array, 0, effectivePositions - 1);
        reverse(array, effectivePositions, length - 1);

        return array;
    }

    /**
     * Reverses the elements of the given array between the specified indices (inclusive).
     *
     * @param array the array whose elements are to be reversed
     * @param start the starting index (inclusive)
     * @param end the ending index (inclusive)
     */
    private static void reverse(int[] array, int start, int end) {
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }
}