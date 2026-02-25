package com.thealgorithms.others;

/**
 * Utility class for array operations.
 */
public final class ArrayRotator {

    private ArrayRotator() {
        // Prevent instantiation
    }

    /**
     * Rotates the given array to the right by the specified number of positions.
     *
     * @param array         the array to rotate
     * @param rotationCount the number of positions to rotate
     * @return the rotated array
     * @throws IllegalArgumentException if the array is null, empty, or rotationCount is negative
     */
    public static int[] rotateArray(int[] array, int rotationCount) {
        if (array == null || array.length == 0 || rotationCount < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int length = array.length;
        int effectiveRotation = rotationCount % length;

        reverseRange(array, 0, length - 1);
        reverseRange(array, 0, effectiveRotation - 1);
        reverseRange(array, effectiveRotation, length - 1);

        return array;
    }

    /**
     * Reverses the elements of the array between the specified indices (inclusive).
     *
     * @param array the array whose subarray is to be reversed
     * @param left  the starting index of the subarray
     * @param right the ending index of the subarray
     */
    private static void reverseRange(int[] array, int left, int right) {
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}