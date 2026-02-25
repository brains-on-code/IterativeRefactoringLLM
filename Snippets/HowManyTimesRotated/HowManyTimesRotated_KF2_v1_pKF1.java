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
        int lowIndex = 0;
        int highIndex = numbers.length - 1;
        int middleIndex = 0;

        while (lowIndex <= highIndex) {
            middleIndex = lowIndex + (highIndex - lowIndex) / 2;

            if (numbers[middleIndex] < numbers[middleIndex - 1]
                    && numbers[middleIndex] < numbers[middleIndex + 1]) {
                break;
            } else if (numbers[middleIndex] > numbers[middleIndex - 1]
                    && numbers[middleIndex] < numbers[middleIndex + 1]) {
                highIndex = middleIndex + 1;
            } else if (numbers[middleIndex] > numbers[middleIndex - 1]
                    && numbers[middleIndex] > numbers[middleIndex + 1]) {
                lowIndex = middleIndex - 1;
            }
        }

        return middleIndex;
    }
}