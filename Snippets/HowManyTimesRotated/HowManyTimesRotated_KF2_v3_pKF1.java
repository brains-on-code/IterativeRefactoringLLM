package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
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
        int leftIndex = 0;
        int rightIndex = numbers.length - 1;
        int rotationIndex = 0;

        while (leftIndex <= rightIndex) {
            rotationIndex = leftIndex + (rightIndex - leftIndex) / 2;

            if (numbers[rotationIndex] < numbers[rotationIndex - 1]
                    && numbers[rotationIndex] < numbers[rotationIndex + 1]) {
                break;
            } else if (numbers[rotationIndex] > numbers[rotationIndex - 1]
                    && numbers[rotationIndex] < numbers[rotationIndex + 1]) {
                rightIndex = rotationIndex + 1;
            } else if (numbers[rotationIndex] > numbers[rotationIndex - 1]
                    && numbers[rotationIndex] > numbers[rotationIndex + 1]) {
                leftIndex = rotationIndex - 1;
            }
        }

        return rotationIndex;
    }
}