package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Utility class to determine how many times a sorted array
 * has been rotated. The number of rotations is equal to the
 * index of the minimum element in the rotated sorted array.
 *
 * Example:
 *   Original sorted array: [2, 5, 6, 8, 11, 12, 15, 18]
 *   Rotated array:         [11, 12, 15, 18, 2, 5, 6, 8]
 *   Rotations: 4 (index of element 2)
 */
final class RotationCount {

    private RotationCount() {
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
     * Returns the number of rotations in a rotated sorted array using binary search.
     * The number of rotations is the index of the minimum element.
     *
     * Assumptions:
     *  - The array was originally sorted in ascending order.
     *  - The array has been rotated 0 or more times.
     *  - All elements are distinct.
     *
     * @param array the rotated sorted array
     * @return the index of the minimum element (number of rotations)
     */
    public static int countRotations(int[] array) {
        int left = 0;
        int right = array.length - 1;
        int length = array.length;

        while (left <= right) {
            // Current segment is already sorted; leftmost element is the minimum
            if (array[left] <= array[right]) {
                return left;
            }

            int mid = left + (right - left) / 2;
            int prev = (mid - 1 + length) % length;
            int next = (mid + 1) % length;

            // Mid is the minimum if it is smaller than both neighbors
            if (array[mid] <= array[prev] && array[mid] <= array[next]) {
                return mid;
            }

            // Left half is sorted; minimum must be in the right half
            if (array[left] <= array[mid]) {
                left = mid + 1;
            } else {
                // Right half is sorted; minimum must be in the left half
                right = mid - 1;
            }
        }

        // Input does not satisfy assumptions (e.g., not a rotated sorted array)
        return -1;
    }
}