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
        validateInput(array, k);

        int length = array.length;
        int rotation = k % length;

        if (rotation == 0 || length == 1) {
            return array;
        }

        reverse(array, 0, length - 1);
        reverse(array, 0, rotation - 1);
        reverse(array, rotation, length - 1);

        return array;
    }

    private static void validateInput(int[] array, int k) {
        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty.");
        }
        if (k < 0) {
            throw new IllegalArgumentException("Rotation count must be non-negative.");
        }
    }

    /**
     * Reverses the elements of the given array between the specified indices (inclusive).
     *
     * @param array the array whose elements are to be reversed
     * @param left  the starting index (inclusive)
     * @param right the ending index (inclusive)
     */
    private static void reverse(int[] array, int left, int right) {
        while (left < right) {
            swap(array, left, right);
            left++;
            right--;
        }
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}