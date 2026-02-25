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

            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();

            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int[] arrayAfterInsert = insertAt(array, insertIndex, elementToInsert);
            System.out.println("Array after insertion:");
            printArray(arrayAfterInsert);

            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            int[] arrayAfterDelete = deleteAt(arrayAfterInsert, deleteIndex);
            System.out.println("Array after deletion:");
            printArray(arrayAfterDelete);
        }
    }

    private static int[] readArray(Scanner scanner) {
        System.out.println("Enter the size of the array");
        int size = scanner.nextInt();

        int[] array = new int[size];
        System.out.println("Enter the elements");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static int[] insertAt(int[] array, int index, int value) {
        if (index < 0 || index > array.length) {
            throw new IndexOutOfBoundsException("Insert index out of bounds: " + index);
        }

        int[] result = new int[array.length + 1];

        System.arraycopy(array, 0, result, 0, index);
        result[index] = value;
        System.arraycopy(array, index, result, index + 1, array.length - index);

        return result;
    }

    private static int[] deleteAt(int[] array, int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Delete index out of bounds: " + index);
        }

        int[] result = new int[array.length - 1];

        System.arraycopy(array, 0, result, 0, index);
        if (index < array.length - 1) {
            System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        }

        return result;
    }

    private static void printArray(int[] array) {
        Arrays.stream(array).forEach(System.out::println);
    }
}