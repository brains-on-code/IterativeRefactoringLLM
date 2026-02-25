package com.thealgorithms.searches;

import java.util.Scanner;

final class HowManyTimesRotated {

    private HowManyTimesRotated() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrayLength = scanner.nextInt();
        int[] values = new int[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            values[i] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + countRotations(values) + " times");
        scanner.close();
    }

    public static int countRotations(int[] values) {
        int left = 0;
        int right = values.length - 1;
        int pivotIndex = 0;

        while (left <= right) {
            pivotIndex = left + (right - left) / 2;

            if (values[pivotIndex] < values[pivotIndex - 1]
                    && values[pivotIndex] < values[pivotIndex + 1]) {
                break;
            } else if (values[pivotIndex] > values[pivotIndex - 1]
                    && values[pivotIndex] < values[pivotIndex + 1]) {
                right = pivotIndex + 1;
            } else if (values[pivotIndex] > values[pivotIndex - 1]
                    && values[pivotIndex] > values[pivotIndex + 1]) {
                left = pivotIndex - 1;
            }
        }

        return pivotIndex;
    }
}