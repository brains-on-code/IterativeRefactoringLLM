package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
        // Prevent instantiation
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
     * Returns the number of times a sorted array has been rotated.
     * Assumes the array was originally sorted in ascending order and then rotated.
     *
     * @param array the rotated sorted array
     * @return the index of the minimum element (number of rotations)
     */
    public static int countRotations(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int low = 0;
        int high = array.length - 1;

        // If the array is not rotated at all
        if (array[low] <= array[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int next = (mid + 1) % array.length;
            int prev = (mid - 1 + array.length) % array.length;

            // Check if mid is the minimum element
            if (array[mid] <= array[next] && array[mid] <= array[prev]) {
                return mid;
            }

            // Decide which half to choose for the next step
            if (array[mid] >= array[low]) {
                // Left half is sorted, so the pivot must be in the right half
                low = mid + 1;
            } else {
                // Right half is sorted, so the pivot must be in the left half
                high = mid - 1;
            }
        }

        return 0;
    }
}