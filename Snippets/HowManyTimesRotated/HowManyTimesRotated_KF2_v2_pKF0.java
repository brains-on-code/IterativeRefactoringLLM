package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
        // Utility class; prevent instantiation
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

    public static int countRotations(int[] array) {
        validateArray(array);

        int low = 0;
        int high = array.length - 1;

        if (isNotRotated(array, low, high)) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (isPivot(array, mid)) {
                return mid;
            }

            if (array[mid] <= array[high]) {
                high = mid - 1;
            } else if (array[mid] >= array[low]) {
                low = mid + 1;
            }
        }

        return 0;
    }

    private static void validateArray(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    private static boolean isNotRotated(int[] array, int low, int high) {
        return array[low] <= array[high];
    }

    private static boolean isPivot(int[] array, int mid) {
        int length = array.length;
        int next = (mid + 1) % length;
        int prev = (mid - 1 + length) % length;
        return array[mid] <= array[next] && array[mid] <= array[prev];
    }
}