package com.thealgorithms.others;

public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Prevent instantiation of utility class
    }

    /**
     * Rotates the given array to the right by {@code k} positions in-place.
     *
     * @param arr the array to rotate
     * @param k   number of positions to rotate to the right
     * @return the rotated array (same instance as {@code arr})
     * @throws IllegalArgumentException if {@code arr} is null, empty, or if {@code k} is negative
     */
    public static int[] rotateRight(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int n = arr.length;
        int steps = k % n;

        if (steps == 0) {
            return arr;
        }

        reverseArray(arr, 0, n - 1);
        reverseArray(arr, 0, steps - 1);
        reverseArray(arr, steps, n - 1);

        return arr;
    }

    /**
     * Reverses the elements of {@code arr} between indices {@code start} and {@code end}, inclusive.
     *
     * @param arr   the array whose elements will be reversed
     * @param start starting index of the segment to reverse
     * @param end   ending index of the segment to reverse
     */
    private static void reverseArray(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}