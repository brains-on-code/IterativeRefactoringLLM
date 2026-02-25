package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int size = scanner.nextInt();
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = scanner.nextInt();
            }

            int rotations = countRotations(array);
            System.out.println("The array has been rotated " + rotations + " times");
        }
    }

    /**
     * Computes how many times a sorted array has been rotated.
     *
     * <p>The array is assumed to have been originally sorted in ascending order
     * and then rotated some number of times. The number of rotations is equal
     * to the index of the minimum element (the rotation pivot).
     *
     * @param array a rotated array that was originally sorted in ascending order
     * @return the index of the minimum element (number of rotations)
     * @throws IllegalArgumentException if {@code array} is {@code null} or empty
     */
    public static int countRotations(int[] array) {
        validateArray(array);

        int low = 0;
        int high = array.length - 1;

        if (isAlreadySorted(array, low, high)) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (isPivot(array, mid)) {
                return mid;
            }

            if (isLeftHalfSorted(array, low, mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return 0;
    }

    private static void validateArray(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    private static boolean isAlreadySorted(int[] array, int low, int high) {
        return array[low] <= array[high];
    }

    private static boolean isPivot(int[] array, int mid) {
        int length = array.length;
        int next = (mid + 1) % length;
        int prev = (mid - 1 + length) % length;
        return array[mid] <= array[next] && array[mid] <= array[prev];
    }

    private static boolean isLeftHalfSorted(int[] array, int low, int mid) {
        return array[mid] >= array[low];
    }
}