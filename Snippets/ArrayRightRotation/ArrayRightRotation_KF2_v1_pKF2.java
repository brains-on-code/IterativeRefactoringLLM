package com.thealgorithms.others;

public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Utility class; prevent instantiation
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
        int steps = k % n; // Normalize rotations greater than array length

        if (steps == 0) {
            return arr; // No rotation needed
        }

        // Reverse the entire array
        reverseArray(arr, 0, n - 1);
        // Reverse the first 'steps' elements
        reverseArray(arr, 0, steps - 1);
        // Reverse the remaining elements
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