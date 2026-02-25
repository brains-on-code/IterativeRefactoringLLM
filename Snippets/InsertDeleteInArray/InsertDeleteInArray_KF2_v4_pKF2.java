package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] originalArray = readArray(scanner);
            int[] arrayAfterInsert = handleInsertion(scanner, originalArray);
            System.out.println("Array after insertion:");
            printArray(arrayAfterInsert);

            int[] arrayAfterDelete = handleDeletion(scanner, arrayAfterInsert);
            System.out.println("Array after deletion:");
            printArray(arrayAfterDelete);
        }
    }

    private static int[] readArray(Scanner scanner) {
        System.out.println("Enter the size of the array:");
        int size = scanner.nextInt();

        int[] array = new int[size];
        System.out.println("Enter " + size + " elements:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static int[] handleInsertion(Scanner scanner, int[] array) {
        System.out.println("Enter the index at which the element should be inserted:");
        int insertIndex = scanner.nextInt();

        System.out.println("Enter the element to be inserted:");
        int elementToInsert = scanner.nextInt();

        return insertAtIndex(array, insertIndex, elementToInsert);
    }

    private static int[] handleDeletion(Scanner scanner, int[] array) {
        System.out.println("Enter the index at which element is to be deleted:");
        int deleteIndex = scanner.nextInt();

        return deleteAtIndex(array, deleteIndex);
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