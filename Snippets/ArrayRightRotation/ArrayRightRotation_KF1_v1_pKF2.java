package com.thealgorithms.others;

/**
 * Utility class for array operations.
 *
 * <p>Currently provides an in-place right rotation of an integer array using
 * the reversal algorithm.</p>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Rotates the given array to the right by {@code var2} positions in-place.
     *
     * <p>Example: array = [1, 2, 3, 4, 5], var2 = 2 → [4, 5, 1, 2, 3]</p>
     *
     * @param var1 the array to rotate (must not be {@code null} or empty)
     * @param var2 number of positions to rotate to the right (must be non-negative)
     * @return the rotated array (same instance as {@code var1})
     * @throws IllegalArgumentException if {@code var1} is {@code null}, empty, or {@code var2} is negative
     */
    public static int[] method1(int[] var1, int var2) {
        if (var1 == null || var1.length == 0 || var2 < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int length = var1.length;
        var2 = var2 % length; // Normalize rotation steps

        if (var2 == 0) {
            return var1; // No rotation needed
        }

        reverse(var1, 0, length - 1);
        reverse(var1, 0, var2 - 1);
        reverse(var1, var2, length - 1);

        return var1;
    }

    /**
     * Reverses the elements of the array between the given indices (inclusive).
     *
     * @param array the array whose elements will be reversed
     * @param left  starting index (inclusive)
     * @param right ending index (inclusive)
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