package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
        // Utility class; prevent instantiation
    }

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
     * <p>
     * The array is assumed to have been originally sorted in ascending order,
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

        // Array is already sorted in ascending order (no rotation)
        if (array[low] <= array[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int next = (mid + 1) % array.length;
            int prev = (mid - 1 + array.length) % array.length;

            // If array[mid] is less than or equal to both neighbors,
            // it is the minimum element (rotation pivot).
            if (array[mid] <= array[next] && array[mid] <= array[prev]) {
                return mid;
            }

            // If left half [low..mid] is sorted, pivot must be in right half [mid+1..high].
            if (array[mid] >= array[low]) {
                low = mid + 1;
            } else {
                // Otherwise, right half [mid..high] is sorted, so pivot is in left half [low..mid-1].
                high = mid - 1;
            }
        }

        // Fallback; for a valid rotated sorted array this line should not be reached.
        return 0;
    }
}