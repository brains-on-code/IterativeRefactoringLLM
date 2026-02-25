package com.thealgorithms.others;

/**
 * Utility class for performing right rotations on integer arrays.
 *
 * <p>A right rotation by {@code k} positions moves each element {@code k} steps to the right.
 * Elements that move past the end of the array wrap around to the beginning.
 *
 * <p>Example (right rotation by 2):
 * <pre>
 * [1, 2, 3, 4, 5] -> [4, 5, 1, 2, 3]
 * </pre>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Rotation_(array)">Array rotation</a>
 */
public final class ArrayRightRotation {

    private ArrayRightRotation() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Rotates the given array to the right by {@code k} positions in-place.
     *
     * @param arr the array to rotate; must not be {@code null} or empty
     * @param k   the number of positions to rotate to the right; must be non-negative
     * @return the same array instance, rotated in-place
     * @throws IllegalArgumentException if {@code arr} is {@code null}, empty, or if {@code k} is negative
     */
    public static int[] rotateRight(int[] arr, int k) {
        validateInput(arr, k);

        int length = arr.length;
        int rotation = k % length;

        if (rotation == 0) {
            return arr;
        }

        reverseRange(arr, 0, length - 1);
        reverseRange(arr, 0, rotation - 1);
        reverseRange(arr, rotation, length - 1);

        return arr;
    }

    private static void validateInput(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must be non-null and non-empty");
        }
        if (k < 0) {
            throw new IllegalArgumentException("k must be non-negative");
        }
    }

    /**
     * Reverses the elements of the given array between the specified indices (inclusive).
     *
     * @param arr   the array whose elements are to be reversed
     * @param start the starting index (inclusive)
     * @param end   the ending index (inclusive)
     */
    private static void reverseRange(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    private static void swap(int[] arr, int firstIndex, int secondIndex) {
        int temp = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = temp;
    }
}