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
     * Returns how many times a sorted array has been rotated.
     *
     * <p>The array is assumed to have been originally sorted in ascending order,
     * then rotated some number of times. The number of rotations is equal to
     * the index of the minimum element.
     *
     * @param array a rotated, originally ascending-sorted array
     * @return the index of the minimum element (number of rotations)
     * @throws IllegalArgumentException if {@code array} is {@code null} or empty
     */
    public static int countRotations(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int low = 0;
        int high = array.length - 1;

        // Already sorted in ascending order (no rotation)
        if (array[low] <= array[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int next = (mid + 1) % array.length;
            int prev = (mid - 1 + array.length) % array.length;

            // Minimum element (rotation pivot)
            if (array[mid] <= array[next] && array[mid] <= array[prev]) {
                return mid;
            }

            // Left half is sorted; pivot is in right half
            if (array[mid] >= array[low]) {
                low = mid + 1;
            } else {
                // Right half is sorted; pivot is in left half
                high = mid - 1;
            }
        }

        // Should not be reached for a valid rotated sorted array
        return 0;
    }
}