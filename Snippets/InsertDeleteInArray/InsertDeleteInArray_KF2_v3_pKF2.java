package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Enter the size of the array:");
            int size = scanner.nextInt();
            int[] originalArray = new int[size];

            System.out.println("Enter " + size + " elements:");
            for (int i = 0; i < size; i++) {
                originalArray[i] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted:");
            int insertIndex = scanner.nextInt();

            System.out.println("Enter the element to be inserted:");
            int elementToInsert = scanner.nextInt();

            int[] arrayAfterInsert = insertAtIndex(originalArray, insertIndex, elementToInsert);

            System.out.println("Array after insertion:");
            printArray(arrayAfterInsert);

            System.out.println("Enter the index at which element is to be deleted:");
            int deleteIndex = scanner.nextInt();

            int[] arrayAfterDelete = deleteAtIndex(arrayAfterInsert, deleteIndex);

            System.out.println("Array after deletion:");
            printArray(arrayAfterDelete);
        }
    }

    private static int[] insertAtIndex(int[] array, int index, int element) {
        int[] result = new int[array.length + 1];

        for (int i = 0; i < result.length; i++) {
            if (i < index) {
                result[i] = array[i];
            } else if (i == index) {
                result[i] = element;
            } else {
                result[i] = array[i - 1];
            }
        }

        return result;
    }

    private static int[] deleteAtIndex(int[] array, int index) {
        int[] result = new int[array.length - 1];

        for (int i = 0; i < result.length; i++) {
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