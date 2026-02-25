package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] array = readInitialArray(scanner);

            int insertIndex = readIndex(scanner, "Enter the index at which the element should be inserted");
            int elementToInsert = readInt(scanner, "Enter the element to be inserted");
            int[] arrayAfterInsert = insertElement(array, insertIndex, elementToInsert);
            printArray("Array after insertion:", arrayAfterInsert);

            int deleteIndex = readIndex(scanner, "Enter the index at which element is to be deleted");
            int[] arrayAfterDelete = deleteElement(arrayAfterInsert, deleteIndex);
            printArray("Array after deletion:", arrayAfterDelete);
        }
    }

    private static int[] readInitialArray(Scanner scanner) {
        int size = readInt(scanner, "Enter the size of the array");

        int[] array = new int[size];
        System.out.println("Enter the elements of the array");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    private static int readIndex(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    private static int[] insertElement(int[] original, int insertIndex, int elementToInsert) {
        if (insertIndex < 0 || insertIndex > original.length) {
            throw new IndexOutOfBoundsException("Insert index out of bounds");
        }

        int[] result = new int[original.length + 1];

        System.arraycopy(original, 0, result, 0, insertIndex);
        result[insertIndex] = elementToInsert;
        System.arraycopy(original, insertIndex, result, insertIndex + 1, original.length - insertIndex);

        return result;
    }

    private static int[] deleteElement(int[] original, int deleteIndex) {
        if (deleteIndex < 0 || deleteIndex >= original.length) {
            throw new IndexOutOfBoundsException("Delete index out of bounds");
        }

        int[] result = new int[original.length - 1];

        System.arraycopy(original, 0, result, 0, deleteIndex);
        System.arraycopy(original, deleteIndex + 1, result, deleteIndex, original.length - deleteIndex - 1);

        return result;
    }

    private static void printArray(String message, int[] array) {
        System.out.println(message);
        System.out.println(Arrays.toString(array));
    }
}