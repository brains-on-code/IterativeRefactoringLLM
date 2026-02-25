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
            for (int index = 0; index < arraySize; index++) {
                System.out.println("Enter the element");
                originalArray[index] = scanner.nextInt();
            }

            // Insert a new element (create a new array)
            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newArraySize = arraySize + 1;
            int[] arrayAfterInsertion = new int[newArraySize];

            for (int index = 0; index < newArraySize; index++) {
                if (index < insertIndex) {
                    arrayAfterInsertion[index] = originalArray[index];
                } else if (index == insertIndex) {
                    arrayAfterInsertion[index] = elementToInsert;
                } else {
                    arrayAfterInsertion[index] = originalArray[index - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int value : arrayAfterInsertion) {
                System.out.println(value);
            }

            // Delete an element at a given index
            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int index = deleteIndex; index < newArraySize - 1; index++) {
                arrayAfterInsertion[index] = arrayAfterInsertion[index + 1];
            }

            System.out.println("Array after deletion:");
            for (int index = 0; index < newArraySize - 1; index++) {
                System.out.println(arrayAfterInsertion[index]);
            }
        }
    }
}