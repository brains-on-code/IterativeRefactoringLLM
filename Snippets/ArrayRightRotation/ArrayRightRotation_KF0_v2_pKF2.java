package com.thealgorithms.others;

/**
 * Utility class that provides a method to perform a right rotation on an array.
 *
 * <p>A right rotation by {@code k} positions moves each element {@code k} steps to the right.
 * Elements that "fall off" the end are wrapped around to the beginning.
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
        // Utility class; prevent instantiation
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

        int n = arr.length;
        int effectiveRotation = k % n;

        if (effectiveRotation == 0) {
            return arr;
        }

        reverseArray(arr, 0, n - 1);
        reverseArray(arr, 0, effectiveRotation - 1);
        reverseArray(arr, effectiveRotation, n - 1);

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
    private static void reverseArray(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}