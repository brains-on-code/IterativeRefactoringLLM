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
        int[] array = new int[arrayLength];

        for (int index = 0; index < arrayLength; index++) {
            array[index] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + findRotationCount(array) + " times");
        scanner.close();
    }

    public static int findRotationCount(int[] array) {
        int startIndex = 0;
        int endIndex = array.length - 1;
        int middleIndex = 0;

        while (startIndex <= endIndex) {
            middleIndex = startIndex + (endIndex - startIndex) / 2;

            if (array[middleIndex] < array[middleIndex - 1] && array[middleIndex] < array[middleIndex + 1]) {
                break;
            } else if (array[middleIndex] > array[middleIndex - 1] && array[middleIndex] < array[middleIndex + 1]) {
                endIndex = middleIndex + 1;
            } else if (array[middleIndex] > array[middleIndex - 1] && array[middleIndex] > array[middleIndex + 1]) {
                startIndex = middleIndex - 1;
            }
        }

        return middleIndex;
    }
}