package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int arraySize = scanner.nextInt();
            int[] originalArray = new int[arraySize];

            // Read initial elements
            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter the element");
                originalArray[i] = scanner.nextInt();
            }

            // Insert a new element (create a new array)
            System.out.println("Enter the index at which the element should be inserted");
            int insertionIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int valueToInsert = scanner.nextInt();

            int updatedArraySize = arraySize + 1;
            int[] arrayAfterInsertion = new int[updatedArraySize];

            for (int i = 0; i < updatedArraySize; i++) {
                if (i < insertionIndex) {
                    arrayAfterInsertion[i] = originalArray[i];
                } else if (i == insertionIndex) {
                    arrayAfterInsertion[i] = valueToInsert;
                } else {
                    arrayAfterInsertion[i] = originalArray[i - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int value : arrayAfterInsertion) {
                System.out.println(value);
            }

            // Delete an element at a given index
            System.out.println("Enter the index at which element is to be deleted");
            int deletionIndex = scanner.nextInt();

            for (int i = deletionIndex; i < updatedArraySize - 1; i++) {
                arrayAfterInsertion[i] = arrayAfterInsertion[i + 1];
            }

            System.out.println("Array after deletion:");
            for (int i = 0; i < updatedArraySize - 1; i++) {
                System.out.println(arrayAfterInsertion[i]);
            }
        }
    }
}