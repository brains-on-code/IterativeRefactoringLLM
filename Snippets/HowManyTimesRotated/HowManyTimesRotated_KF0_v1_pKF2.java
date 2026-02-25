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
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }

            System.out.println("The array has been rotated " + rotated(a) + " times");
        }
    }

    /**
     * Returns the number of times the array has been rotated.
     * This is the index of the minimum element in the rotated sorted array.
     *
     * @param a the rotated sorted array (no duplicates)
     * @return the rotation count (index of the minimum element)
     * @throws IllegalArgumentException if the array is null or empty
     */
    public static int rotated(int[] a) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int low = 0;
        int high = a.length - 1;

        // If the array is already sorted (not rotated), the first element is the minimum.
        if (a[low] <= a[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int prev = (mid - 1 + a.length) % a.length;
            int next = (mid + 1) % a.length;

            // Check if mid is the minimum element.
            if (a[mid] <= a[prev] && a[mid] <= a[next]) {
                return mid;
            }

            // Decide which half to search next.
            if (a[mid] >= a[low]) {
                // Left half is sorted; minimum must be in the right half.
                low = mid + 1;
            } else {
                // Right half is sorted; minimum must be in the left half.
                high = mid - 1;
            }
        }

        // This should not be reached if input constraints are satisfied.
        return 0;
    }
}