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
     * Performs a right rotation on the given array by the specified number of positions.
     *
     * @param array the array to be rotated
     * @param rotationCount the number of positions to rotate the array to the right
     * @return the same array instance, rotated in-place
     * @throws IllegalArgumentException if the array is null, empty, or rotationCount is negative
     */
    public static int[] rotateRight(int[] array, int rotationCount) {
        if (array == null || array.length == 0 || rotationCount < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int length = array.length;
        int steps = rotationCount % length;

        reverseRange(array, 0, length - 1);
        reverseRange(array, 0, steps - 1);
        reverseRange(array, steps, length - 1);

        return array;
    }

    /**
     * Reverses the elements of the array between the specified indices (inclusive).
     *
     * @param array the array to be reversed
     * @param startIndex starting index (inclusive)
     * @param endIndex ending index (inclusive)
     */
    private static void reverseRange(int[] array, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;

        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}