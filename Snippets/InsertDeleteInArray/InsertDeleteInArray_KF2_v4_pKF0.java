package com.thealgorithms.others;

import java.util.Arrays;
import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] array = readArray(scanner);

            int insertIndex = readInt(scanner, "Enter the index at which the element should be inserted");
            int elementToInsert = readInt(scanner, "Enter the element to be inserted");

            int[] arrayAfterInsert = insertAt(array, insertIndex, elementToInsert);
            System.out.println("Array after insertion:");
            printArray(arrayAfterInsert);

            int deleteIndex = readInt(scanner, "Enter the index at which element is to be deleted");

            int[] arrayAfterDelete = deleteAt(arrayAfterInsert, deleteIndex);
            System.out.println("Array after deletion:");
            printArray(arrayAfterDelete);
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextInt();
    }

    private static int[] readArray(Scanner scanner) {
        int size = readInt(scanner, "Enter the size of the array");

        int[] array = new int[size];
        System.out.println("Enter the elements:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static int[] insertAt(int[] array, int index, int value) {
        validateInsertIndex(array.length, index);

        int[] result = new int[array.length + 1];

        System.arraycopy(array, 0, result, 0, index);
        result[index] = value;
        System.arraycopy(array, index, result, index + 1, array.length - index);

        return result;
    }

    private static int[] deleteAt(int[] array, int index) {
        validateDeleteIndex(array.length, index);

        int[] result = new int[array.length - 1];

        System.arraycopy(array, 0, result, 0, index);
        if (index < array.length - 1) {
            System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        }

        return result;
    }

    private static void validateInsertIndex(int length, int index) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Insert index out of bounds: " + index);
        }
    }

    private static void validateDeleteIndex(int length, int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Delete index out of bounds: " + index);
        }
    }

    private static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }
}