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
            int[] array = readArray(scanner, size);

            int rotations = countRotations(array);
            System.out.println("The array has been rotated " + rotations + " times");
        }
    }

    private static int[] readArray(Scanner scanner, int size) {
        int[] array = new int[size];
        for (int index = 0; index < size; index++) {
            array[index] = scanner.nextInt();
        }
        return array;
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
            int middle = left + (right - left) / 2;

            if (isRotationPoint(array, middle)) {
                return middle;
            }

            if (isLeftHalfSorted(array, left, middle)) {
                left = middle + 1;
            } else {
                right = middle - 1;
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

    /**
     * Checks if the element at the given index is the rotation point
     * (i.e., the minimum element in the rotated sorted array).
     */
    private static boolean isRotationPoint(int[] array, int index) {
        return index > 0 && array[index] < array[index - 1];
    }

    private static boolean isLeftHalfSorted(int[] array, int left, int middle) {
        return array[middle] >= array[left];
    }
}