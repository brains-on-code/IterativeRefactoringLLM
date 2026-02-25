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

        reverseSubarray(array, 0, length - 1);
        reverseSubarray(array, 0, effectiveRotation - 1);
        reverseSubarray(array, effectiveRotation, length - 1);

        return array;
    }

    /**
     * Reverses the elements of the array between the specified indices (inclusive).
     *
     * @param array      the array whose subarray is to be reversed
     * @param startIndex the starting index of the subarray
     * @param endIndex   the ending index of the subarray
     */
    private static void reverseSubarray(int[] array, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            int temp = array[startIndex];
            array[startIndex] = array[endIndex];
            array[endIndex] = temp;
            startIndex++;
            endIndex--;
        }
    }
}