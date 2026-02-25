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

        int start = 0;
        int end = array.length - 1;

        if (isSortedWithoutRotation(array, start, end)) {
            return 0;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (isPivot(array, mid)) {
                return mid;
            }

            if (array[mid] <= array[end]) {
                end = mid - 1;
            } else if (array[mid] >= array[start]) {
                start = mid + 1;
            }
        }

        return 0;
    }

    private static void validateArray(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
    }

    private static boolean isSortedWithoutRotation(int[] array, int start, int end) {
        return array[start] <= array[end];
    }

    private static boolean isPivot(int[] array, int mid) {
        int length = array.length;
        int nextIndex = (mid + 1) % length;
        int prevIndex = (mid - 1 + length) % length;
        int midValue = array[mid];

        return midValue <= array[nextIndex] && midValue <= array[prevIndex];
    }
}