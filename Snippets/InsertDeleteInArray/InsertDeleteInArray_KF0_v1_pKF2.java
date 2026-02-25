package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array:");
            int size = scanner.nextInt();
            int[] originalArray = new int[size];

            // Read initial elements
            for (int i = 0; i < size; i++) {
                System.out.println("Enter element " + i + ":");
                originalArray[i] = scanner.nextInt();
            }

            // Insert a new element (create a new array with size + 1)
            System.out.println("Enter the index at which the element should be inserted:");
            int insertIndex = scanner.nextInt();

            System.out.println("Enter the element to be inserted:");
            int elementToInsert = scanner.nextInt();

            int[] arrayAfterInsert = insertElement(originalArray, insertIndex, elementToInsert);

            System.out.println("Array after insertion:");
            printArray(arrayAfterInsert);

            // Delete an element at a given index (shift elements left)
            System.out.println("Enter the index at which element is to be deleted:");
            int deleteIndex = scanner.nextInt();

            int[] arrayAfterDelete = deleteElement(arrayAfterInsert, deleteIndex);

            System.out.println("Array after deletion:");
            printArray(arrayAfterDelete);
        }
    }

    private static int[] insertElement(int[] array, int index, int value) {
        int newSize = array.length + 1;
        int[] result = new int[newSize];

        for (int i = 0; i < newSize; i++) {
            if (i < index) {
                result[i] = array[i];
            } else if (i == index) {
                result[i] = value;
            } else {
                result[i] = array[i - 1];
            }
        }

        return result;
    }

    private static int[] deleteElement(int[] array, int index) {
        int newSize = array.length - 1;
        int[] result = new int[newSize];

        for (int i = 0; i < newSize; i++) {
            if (i < index) {
                result[i] = array[i];
            } else {
                result[i] = array[i + 1];
            }
        }

        return result;
    }

    private static void printArray(int[] array) {
        for (int value : array) {
            System.out.println(value);
        }
    }
}