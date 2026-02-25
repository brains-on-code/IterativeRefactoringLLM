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

        for (int i = 0; i < arrayLength; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + findRotationCount(numbers) + " times");
        scanner.close();
    }

    public static int findRotationCount(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        int pivotIndex = 0;

        while (left <= right) {
            pivotIndex = left + (right - left) / 2;

            boolean isLessThanPrevious = pivotIndex == 0 || numbers[pivotIndex] < numbers[pivotIndex - 1];
            boolean isLessThanNext = pivotIndex == numbers.length - 1 || numbers[pivotIndex] < numbers[pivotIndex + 1];

            if (isLessThanPrevious && isLessThanNext) {
                break;
            } else if (numbers[pivotIndex] >= numbers[left]) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }

        return pivotIndex;
    }
}