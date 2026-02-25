package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Problem Statement:
 * Given a rotated sorted array (no duplicates), determine how many times it has
 * been rotated from its initial sorted position.
 *
 * Example:
 * [11, 12, 15, 18, 2, 5, 6, 8] -> rotated 4 times
 *
 * The number of rotations equals the index of the minimum element.
 */
final class HowManyTimesRotated {

    private HowManyTimesRotated() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int length = scanner.nextInt();
            int[] array = new int[length];

            for (int i = 0; i < length; i++) {
                array[i] = scanner.nextInt();
            }

            int rotations = countRotations(array);
            System.out.println("The array has been rotated " + rotations + " times");
        }
    }

    /**
     * Returns the number of rotations of a sorted array, which is the index of
     * the minimum element, using binary search in O(log N) time.
     *
     * @param array a rotated sorted array with no duplicates
     * @return number of rotations
     */
    public static int countRotations(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int low = 0;
        int high = array.length - 1;

        // If the array is already sorted (not rotated)
        if (array[low] <= array[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int next = (mid + 1) % array.length;
            int prev = (mid - 1 + array.length) % array.length;

            // Check if mid is the minimum element
            if (array[mid] <= array[prev] && array[mid] <= array[next]) {
                return mid;
            }

            // Decide which half to choose for the next step
            if (array[mid] >= array[low]) {
                // Left half is sorted, so minimum must be in right half
                low = mid + 1;
            } else {
                // Right half is sorted, so minimum must be in left half
                high = mid - 1;
            }
        }

        // Should never reach here if input constraints are met
        return 0;
    }
}