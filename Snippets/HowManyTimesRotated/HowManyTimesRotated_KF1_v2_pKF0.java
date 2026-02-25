package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Utility class to determine how many times a sorted array
 * has been rotated. The number of rotations is equal to the
 * index of the minimum element in the rotated sorted array.
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
     * Finds the number of rotations in a rotated sorted array by
     * locating the index of the minimum element using binary search.
     *
     * @param array rotated sorted array
     * @return index of the minimum element (number of rotations)
     */
    public static int countRotations(int[] array) {
        validateInput(array);

        if (isNotRotated(array)) {
            return 0;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (isMinimumElement(array, mid)) {
                return mid;
            }

            if (isLeftHalfSorted(array, left, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return 0;
    }

    private static void validateInput(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    private static boolean isNotRotated(int[] array) {
        return array.length == 1 || array[0] < array[array.length - 1];
    }

    private static boolean isMinimumElement(int[] array, int mid) {
        return mid > 0 && array[mid] < array[mid - 1];
    }

    private static boolean isLeftHalfSorted(int[] array, int left, int mid) {
        return array[mid] >= array[left];
    }
}