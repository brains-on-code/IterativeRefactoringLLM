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
     * @param array     the array to be rotated (must not be null or empty)
     * @param positions the number of positions to rotate the array to the right (must be non-negative)
     * @return the same array instance, rotated to the right
     * @throws IllegalArgumentException if the input array is null, empty, or positions is negative
     */
    public static int[] rotateRight(int[] array, int positions) {
        validateInput(array, positions);

        int length = array.length;
        int shift = positions % length;

        if (shift == 0) {
            return array;
        }

        rotateUsingReversal(array, shift);
        return array;
    }

    private static void rotateUsingReversal(int[] array, int shift) {
        int lastIndex = array.length - 1;

        reverse(array, 0, lastIndex);
        reverse(array, 0, shift - 1);
        reverse(array, shift, lastIndex);
    }

    private static void validateInput(int[] array, int positions) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must be non-null and non-empty.");
        }
        if (positions < 0) {
            throw new IllegalArgumentException("Positions must be non-negative.");
        }
    }

    /**
     * Reverses the elements of the given array between the specified indices (inclusive).
     *
     * @param array the array whose elements are to be reversed
     * @param start the starting index (inclusive)
     * @param end   the ending index (inclusive)
     */
    private static void reverse(int[] array, int start, int end) {
        while (start < end) {
            swap(array, start++, end--);
        }
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}