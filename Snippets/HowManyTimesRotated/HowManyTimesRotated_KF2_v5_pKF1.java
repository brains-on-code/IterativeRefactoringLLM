package com.thealgorithms.searches;

import java.util.Scanner;

final class RotationCounter {

    private RotationCounter() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrayLength = scanner.nextInt();
        int[] numbers = new int[arrayLength];

        for (int index = 0; index < arrayLength; index++) {
            numbers[index] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + countRotations(numbers) + " times");
        scanner.close();
    }

    public static int countRotations(int[] numbers) {
        int startIndex = 0;
        int endIndex = numbers.length - 1;
        int pivotIndex = 0;

        while (startIndex <= endIndex) {
            pivotIndex = startIndex + (endIndex - startIndex) / 2;

            if (numbers[pivotIndex] < numbers[pivotIndex - 1]
                    && numbers[pivotIndex] < numbers[pivotIndex + 1]) {
                break;
            } else if (numbers[pivotIndex] > numbers[pivotIndex - 1]
                    && numbers[pivotIndex] < numbers[pivotIndex + 1]) {
                endIndex = pivotIndex + 1;
            } else if (numbers[pivotIndex] > numbers[pivotIndex - 1]
                    && numbers[pivotIndex] > numbers[pivotIndex + 1]) {
                startIndex = pivotIndex - 1;
            }
        }

        return pivotIndex;
    }
}