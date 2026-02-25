package com.thealgorithms.searches;

import java.util.Scanner;

/*
    This class finds how many times a sorted array has been rotated.
    The rotation count is equal to the index of the minimum element.
 */
final class RotationCountSearch {

    private RotationCountSearch() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrayLength = scanner.nextInt();
        int[] values = new int[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            values[i] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + findRotationCount(values) + " times");
        scanner.close();
    }

    public static int findRotationCount(int[] values) {
        int leftIndex = 0;
        int rightIndex = values.length - 1;
        int middleIndex = 0;

        while (leftIndex <= rightIndex) {
            middleIndex = leftIndex + (rightIndex - leftIndex) / 2;

            if (values[middleIndex] < values[middleIndex - 1] && values[middleIndex] < values[middleIndex + 1]) {
                break;
            } else if (values[middleIndex] > values[middleIndex - 1] && values[middleIndex] < values[middleIndex + 1]) {
                rightIndex = middleIndex + 1;
            } else if (values[middleIndex] > values[middleIndex - 1] && values[middleIndex] > values[middleIndex + 1]) {
                leftIndex = middleIndex - 1;
            }
        }

        return middleIndex;
    }
}