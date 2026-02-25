package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] originalArray = readArray(scanner);

            int insertIndex = readInt(scanner, "Enter the index at which the element should be inserted:");
            int elementToInsert = readInt(scanner, "Enter the element to be inserted:");
            int[] arrayAfterInsert = insertElement(originalArray, insertIndex, elementToInsert);

            System.out.println("Array after insertion:");
            printArray(arrayAfterInsert);

            int deleteIndex = readInt(scanner, "Enter the index at which element is to be deleted:");
            int[] arrayAfterDelete = deleteElement(arrayAfterInsert, deleteIndex);

            System.out.println("Array after deletion:");
            printArray(arrayAfterDelete);
        }
    }

    private static int[] readArray(Scanner scanner) {
        int size = readInt(scanner, "Enter the size of the array:");
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = readInt(scanner, "Enter element " + i + ":");
        }

        return array;
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    private static int[] insertElement(int[] array, int index, int value) {
        int[] result = new int[array.length + 1];

        for (int i = 0; i < result.length; i++) {
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