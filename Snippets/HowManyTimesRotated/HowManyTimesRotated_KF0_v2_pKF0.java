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
            int[] array = readArray(scanner, length);

            int rotations = countRotations(array);
            System.out.println("The array has been rotated " + rotations + " times");
        }
    }

    private static int[] readArray(Scanner scanner, int length) {
        int[] array = new int[length];
        for (int index = 0; index < length; index++) {
            array[index] = scanner.nextInt();
        }
        return array;
    }

    /**
     * Returns the number of rotations of a sorted array, which is the index of
     * the minimum element, using binary search in O(log N) time.
     *
     * @param array a rotated sorted array with no duplicates
     * @return number of rotations
     */
    public static int countRotations(int[] array) {
        validateArray(array);

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

        return 0;
    }

    private static void validateArray(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    private static boolean isAlreadySorted(int[] array, int low, int high) {
        return array[low] <= array[high];
    }

    private static boolean isMinimumElement(int[] array, int mid) {
        int length = array.length;
        int next = (mid + 1) % length;
        int prev = (mid - 1 + length) % length;
        return array[mid] <= array[prev] && array[mid] <= array[next];
    }

    private static boolean isLeftHalfSorted(int[] array, int low, int mid) {
        return array[mid] >= array[low];
    }
}