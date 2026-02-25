package com.thealgorithms.others;

public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Prevent instantiation
    }

    /**
     * Rotates the given array to the right by {@code k} positions in-place.
     *
     * @param arr the array to rotate; must not be {@code null} or empty
     * @param k   number of positions to rotate to the right; must be non-negative
     * @return the same array instance, rotated in-place
     * @throws IllegalArgumentException if {@code arr} is {@code null}, empty, or if {@code k} is negative
     */
    public static int[] rotateRight(int[] arr, int k) {
        validateInput(arr, k);

        int n = arr.length;
        int steps = k % n;

        if (steps == 0) {
            return arr;
        }

        /*
         * Right rotation by k:
         * 1. Reverse the entire array.
         * 2. Reverse the first k elements.
         * 3. Reverse the remaining n - k elements.
         */
        reverseRange(arr, 0, n - 1);
        reverseRange(arr, 0, steps - 1);
        reverseRange(arr, steps, n - 1);

        return arr;
    }

    private static void validateInput(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k < 0) {
            throw new IllegalArgumentException("Rotation steps must be non-negative");
        }
    }

    /**
     * Reverses the elements of {@code arr} between indices {@code start} and {@code end}, inclusive.
     *
     * @param arr   the array whose elements will be reversed
     * @param start starting index of the segment to reverse (inclusive)
     * @param end   ending index of the segment to reverse (inclusive)
     */
    private static void reverseRange(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}