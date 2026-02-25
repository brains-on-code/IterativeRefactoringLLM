package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrayLength = scanner.nextInt();
        int[] rotatedArray = new int[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            rotatedArray[i] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + countRotations(rotatedArray) + " times");
        scanner.close();
    }

    public static int countRotations(int[] rotatedArray) {
        int left = 0;
        int right = rotatedArray.length - 1;
        int pivotIndex = 0;

        while (left <= right) {
            pivotIndex = left + (right - left) / 2;

            if (rotatedArray[pivotIndex] < rotatedArray[pivotIndex - 1]
                    && rotatedArray[pivotIndex] < rotatedArray[pivotIndex + 1]) {
                break;
            } else if (rotatedArray[pivotIndex] > rotatedArray[pivotIndex - 1]
                    && rotatedArray[pivotIndex] < rotatedArray[pivotIndex + 1]) {
                right = pivotIndex + 1;
            } else if (rotatedArray[pivotIndex] > rotatedArray[pivotIndex - 1]
                    && rotatedArray[pivotIndex] > rotatedArray[pivotIndex + 1]) {
                left = pivotIndex - 1;
            }
        }

        return pivotIndex;
    }
}