package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Utility class to determine how many times a sorted array
 * has been rotated. The number of rotations is equal to the
 * index of the minimum element in the rotated sorted array.
 */
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int size = scanner.nextInt();
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = scanner.nextInt();
            }

            int rotations = method2(array);
            System.out.println("The array has been rotated " + rotations + " times");
        }
    }

    /**
     * Finds the number of rotations in a rotated sorted array by
     * locating the index of the minimum element using binary search.
     *
     * @param array rotated sorted array
     * @return index of the minimum element (number of rotations)
     */
    public static int method2(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (array.length == 1 || array[0] < array[array.length - 1]) {
            // Array is not rotated
            return 0;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if mid is the minimum element
            if (mid > 0 && array[mid] < array[mid - 1]) {
                return mid;
            }

            // Decide which half to continue searching in
            if (array[mid] >= array[left]) {
                // Left half is sorted, so minimum is in right half
                left = mid + 1;
            } else {
                // Right half is sorted, so minimum is in left half
                right = mid - 1;
            }
        }

        // Fallback (should not be reached for valid rotated sorted arrays)
        return 0;
    }
}