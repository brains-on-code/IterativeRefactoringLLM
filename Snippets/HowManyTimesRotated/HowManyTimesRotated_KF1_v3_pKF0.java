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

        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;

            if (isMinimumElement(array, middleIndex)) {
                return middleIndex;
            }

            if (isLeftHalfSorted(array, leftIndex, middleIndex)) {
                leftIndex = middleIndex + 1;
            } else {
                rightIndex = middleIndex - 1;
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

    private static boolean isMinimumElement(int[] array, int middleIndex) {
        return middleIndex > 0 && array[middleIndex] < array[middleIndex - 1];
    }

    private static boolean isLeftHalfSorted(int[] array, int leftIndex, int middleIndex) {
        return array[middleIndex] >= array[leftIndex];
    }
}