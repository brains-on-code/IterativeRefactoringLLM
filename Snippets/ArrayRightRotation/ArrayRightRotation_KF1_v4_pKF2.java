package com.thealgorithms.others;

/**
 * Utility methods for working with integer arrays.
 *
 * <p>Currently provides an in-place right rotation using the reversal algorithm.</p>
 */
public final class ArrayUtils {

    private ArrayUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Rotates the given array to the right by {@code rotationSteps} positions in-place.
     *
     * <p>Example: {@code [1, 2, 3, 4, 5]} rotated by {@code 2} becomes
     * {@code [4, 5, 1, 2, 3]}.</p>
     *
     * @param array         the array to rotate; must not be {@code null} or empty
     * @param rotationSteps number of positions to rotate to the right; must be non-negative
     * @return the same array instance, after rotation
     * @throws IllegalArgumentException if {@code array} is {@code null}, empty,
     *                                  or {@code rotationSteps} is negative
     */
    public static int[] rotateRight(int[] array, int rotationSteps) {
        validateInput(array, rotationSteps);

        int length = array.length;
        int normalizedSteps = rotationSteps % length;

        if (normalizedSteps == 0) {
            return array;
        }

        /*
         * Reversal algorithm for right rotation by k:
         * 1. Reverse the entire array.
         * 2. Reverse the first k elements.
         * 3. Reverse the remaining elements.
         */
        reverse(array, 0, length - 1);
        reverse(array, 0, normalizedSteps - 1);
        reverse(array, normalizedSteps, length - 1);

        return array;
    }

    private static void validateInput(int[] array, int rotationSteps) {
        if (array == null || array.length == 0 || rotationSteps < 0) {
            throw new IllegalArgumentException(
                "Array must be non-null, non-empty, and rotationSteps must be non-negative."
            );
        }
    }

    /**
     * Reverses the elements of {@code array} between {@code left} and {@code right}, inclusive.
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