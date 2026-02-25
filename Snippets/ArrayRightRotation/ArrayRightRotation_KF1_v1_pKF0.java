package com.thealgorithms.others;

/**
 * Utility class for array operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Rotates the given array to the right by the specified number of positions.
     *
     * @param array the array to rotate; must not be null or empty
     * @param k     the number of positions to rotate; must be non-negative
     * @return the rotated array (same instance as the input array)
     * @throws IllegalArgumentException if the input array is null, empty, or k is negative
     */
    public static int[] rotateRight(int[] array, int k) {
        if (array == null || array.length == 0 || k < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int length = array.length;
        k = k % length;

        reverse(array, 0, length - 1);
        reverse(array, 0, k - 1);
        reverse(array, k, length - 1);

        return array;
    }

    /**
     * Reverses the elements of the given array between the specified indices (inclusive).
     *
     * @param array the array whose elements are to be reversed
     * @param left  the starting index
     * @param right the ending index
     */
    private static void reverse(int[] array, int left, int right) {
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}