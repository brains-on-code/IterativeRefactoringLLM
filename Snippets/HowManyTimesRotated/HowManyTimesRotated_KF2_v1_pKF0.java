package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int size = scanner.nextInt();
            int[] array = new int[size];

            for (int index = 0; index < size; index++) {
                array[index] = scanner.nextInt();
            }

            int rotations = countRotations(array);
            System.out.println("The array has been rotated " + rotations + " times");
        }
    }

    public static int countRotations(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int low = 0;
        int high = array.length - 1;

        // If the array is not rotated at all
        if (array[low] <= array[high]) {
            return 0;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int next = (mid + 1) % array.length;
            int prev = (mid - 1 + array.length) % array.length;

            if (array[mid] <= array[next] && array[mid] <= array[prev]) {
                return mid;
            } else if (array[mid] <= array[high]) {
                high = mid - 1;
            } else if (array[mid] >= array[low]) {
                low = mid + 1;
            }
        }

        return 0;
    }
}