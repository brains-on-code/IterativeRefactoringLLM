package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int arraySize = scanner.nextInt();
            int[] numbers = new int[arraySize];

            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter the element");
                numbers[i] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertPosition = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int valueToInsert = scanner.nextInt();

            int[] numbersAfterInsert = new int[arraySize + 1];

            for (int i = 0; i < numbersAfterInsert.length; i++) {
                if (i < insertPosition) {
                    numbersAfterInsert[i] = numbers[i];
                } else if (i == insertPosition) {
                    numbersAfterInsert[i] = valueToInsert;
                } else {
                    numbersAfterInsert[i] = numbers[i - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int value : numbersAfterInsert) {
                System.out.println(value);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deletePosition = scanner.nextInt();

            for (int i = deletePosition; i < numbersAfterInsert.length - 1; i++) {
                numbersAfterInsert[i] = numbersAfterInsert[i + 1];
            }

            System.out.println("Array after deletion:");
            for (int i = 0; i < numbersAfterInsert.length - 1; i++) {
                System.out.println(numbersAfterInsert[i]);
            }
        }
    }
}