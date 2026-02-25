package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] array = readInitialArray(scanner);
            int[] arrayAfterInsert = insertElement(scanner, array);
            printArray(arrayAfterInsert);

            int[] arrayAfterDelete = deleteElement(scanner, arrayAfterInsert);
            printArray(arrayAfterDelete);
        }
    }

    private static int[] readInitialArray(Scanner scanner) {
        System.out.println("Enter the size of the array");
        int size = scanner.nextInt();

        int[] array = new int[size];
        System.out.println("Enter the elements of the array");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static int[] insertElement(Scanner scanner, int[] original) {
        System.out.println("Enter the index at which the element should be inserted");
        int insertIndex = scanner.nextInt();

        System.out.println("Enter the element to be inserted");
        int elementToInsert = scanner.nextInt();

        if (insertIndex < 0 || insertIndex > original.length) {
            throw new IndexOutOfBoundsException("Insert index out of bounds");
        }

        int[] result = new int[original.length + 1];

        for (int i = 0; i < result.length; i++) {
            if (i < insertIndex) {
                result[i] = original[i];
            } else if (i == insertIndex) {
                result[i] = elementToInsert;
            } else {
                result[i] = original[i - 1];
            }
        }

        return result;
    }

    private static int[] deleteElement(Scanner scanner, int[] original) {
        System.out.println("Enter the index at which element is to be deleted");
        int deleteIndex = scanner.nextInt();

        if (deleteIndex < 0 || deleteIndex >= original.length) {
            throw new IndexOutOfBoundsException("Delete index out of bounds");
        }

        int[] result = new int[original.length - 1];

        for (int i = 0, j = 0; i < original.length; i++) {
            if (i == deleteIndex) {
                continue;
            }
            result[j++] = original[i];
        }

        return result;
    }

    private static void printArray(int[] array) {
        System.out.println("Array contents:");
        for (int value : array) {
            System.out.println(value);
        }
    }
}