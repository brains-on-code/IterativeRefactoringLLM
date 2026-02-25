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
        int[] numbers = new int[arrayLength];

        for (int index = 0; index < arrayLength; index++) {
            numbers[index] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + findRotationCount(numbers) + " times");
        scanner.close();
    }

    public static int findRotationCount(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        int middle = 0;

        while (left <= right) {
            middle = left + (right - left) / 2;

            if (numbers[middle] < numbers[middle - 1] && numbers[middle] < numbers[middle + 1]) {
                break;
            } else if (numbers[middle] > numbers[middle - 1] && numbers[middle] < numbers[middle + 1]) {
                right = middle + 1;
            } else if (numbers[middle] > numbers[middle - 1] && numbers[middle] > numbers[middle + 1]) {
                left = middle - 1;
            }
        }

        return middle;
    }
}