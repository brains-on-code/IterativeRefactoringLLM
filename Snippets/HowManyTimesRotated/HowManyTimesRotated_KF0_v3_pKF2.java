package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Utility class for determining how many times a sorted array has been rotated.
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
        validateInput(array);

        int low = 0;
        int high = array.length - 1;

        if (isAlreadySorted(array, low, high)) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (isMinimumElement(array, mid)) {
                return mid;
            }

            if (isLeftHalfSorted(array, low, mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        // Should not occur if input meets assumptions
        return 0;
    }

    private static void validateInput(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    private static boolean isAlreadySorted(int[] array, int low, int high) {
        return array[low] <= array[high];
    }

    private static boolean isMinimumElement(int[] array, int mid) {
        int length = array.length;
        int prev = (mid - 1 + length) % length;
        int next = (mid + 1) % length;
        return array[mid] <= array[prev] && array[mid] <= array[next];
    }

    private static boolean isLeftHalfSorted(int[] array, int low, int mid) {
        return array[mid] >= array[low];
    }
}