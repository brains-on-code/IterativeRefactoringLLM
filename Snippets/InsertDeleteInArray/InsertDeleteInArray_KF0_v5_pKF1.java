package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int initialSize = scanner.nextInt();
            int[] numbers = new int[initialSize];

            // Read initial elements
            for (int index = 0; index < initialSize; index++) {
                System.out.println("Enter the element");
                numbers[index] = scanner.nextInt();
            }

            // Insert a new element (create a new array)
            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int sizeAfterInsertion = initialSize + 1;
            int[] numbersAfterInsertion = new int[sizeAfterInsertion];

            for (int index = 0; index < sizeAfterInsertion; index++) {
                if (index < insertIndex) {
                    numbersAfterInsertion[index] = numbers[index];
                } else if (index == insertIndex) {
                    numbersAfterInsertion[index] = elementToInsert;
                } else {
                    numbersAfterInsertion[index] = numbers[index - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int value : numbersAfterInsertion) {
                System.out.println(value);
            }

            // Delete an element at a given index
            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int index = deleteIndex; index < sizeAfterInsertion - 1; index++) {
                numbersAfterInsertion[index] = numbersAfterInsertion[index + 1];
            }

            System.out.println("Array after deletion:");
            for (int index = 0; index < sizeAfterInsertion - 1; index++) {
                System.out.println(numbersAfterInsertion[index]);
            }
        }
    }
}