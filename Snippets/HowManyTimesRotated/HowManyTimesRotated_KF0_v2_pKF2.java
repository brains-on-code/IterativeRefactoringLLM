package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Determines how many times a sorted array has been rotated.
 *
 * <p>Example:
 * <pre>
 * Input array: [11, 12, 15, 18, 2, 5, 6, 8]
 * Output: 4
 * </pre>
 *
 * <p>Assumptions:
 * <ul>
 *   <li>The array was originally sorted in ascending order.</li>
 *   <li>The array contains no duplicate elements.</li>
 *   <li>Rotation is defined as moving the first element to the end.</li>
 * </ul>
 *
 * <p>The number of rotations is equal to the index of the minimum element.
 * This implementation finds the minimum element using binary search in O(log n) time.
 */
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
     * Returns the number of times the array has been rotated.
     * This is the index of the minimum element in the rotated sorted array.
     *
     * @param array the rotated sorted array (no duplicates)
     * @return the rotation count (index of the minimum element)
     * @throws IllegalArgumentException if the array is null or empty
     */
    public static int countRotations(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int low = 0;
        int high = array.length - 1;

        // Already sorted (no rotation)
        if (array[low] <= array[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int prev = (mid - 1 + array.length) % array.length;
            int next = (mid + 1) % array.length;

            // Mid is the minimum element
            if (array[mid] <= array[prev] && array[mid] <= array[next]) {
                return mid;
            }

            // Left half is sorted; minimum is in the right half
            if (array[mid] >= array[low]) {
                low = mid + 1;
            } else {
                // Right half is sorted; minimum is in the left half
                high = mid - 1;
            }
        }

        // Fallback (should not occur if input meets assumptions)
        return 0;
    }
}