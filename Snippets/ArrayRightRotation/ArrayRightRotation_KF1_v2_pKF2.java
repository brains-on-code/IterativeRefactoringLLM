package com.thealgorithms.others;

/**
 * Utility class for array operations.
 *
 * <p>Currently provides an in-place right rotation of an integer array using
 * the reversal algorithm.</p>
 */
public final class ArrayUtils {

    private ArrayUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Rotates the given array to the right by {@code rotationSteps} positions in-place.
     *
     * <p>Example: array = [1, 2, 3, 4, 5], rotationSteps = 2 → [4, 5, 1, 2, 3]</p>
     *
     * @param array         the array to rotate (must not be {@code null} or empty)
     * @param rotationSteps number of positions to rotate to the right (must be non-negative)
     * @return the rotated array (same instance as {@code array})
     * @throws IllegalArgumentException if {@code array} is {@code null}, empty, or {@code rotationSteps} is negative
     */
    public static int[] rotateRight(int[] array, int rotationSteps) {
        if (array == null || array.length == 0 || rotationSteps < 0) {
            throw new IllegalArgumentException("Array must be non-null, non-empty, and rotationSteps must be non-negative.");
        }

        int length = array.length;
        int normalizedSteps = rotationSteps % length;

        if (normalizedSteps == 0) {
            return array;
        }

        reverse(array, 0, length - 1);
        reverse(array, 0, normalizedSteps - 1);
        reverse(array, normalizedSteps, length - 1);

        return array;
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