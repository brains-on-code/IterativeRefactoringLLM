package com.thealgorithms.others;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] originalArray = readArray(scanner);

            int insertIndex =
                    readBoundedIndex(scanner, "Enter the index at which the element should be inserted", 0,
                            originalArray.length);
            int elementToInsert = readInt(scanner, "Enter the element to be inserted");
            int[] arrayAfterInsertion = insertElement(originalArray, insertIndex, elementToInsert);
            printArray("Array after insertion:", arrayAfterInsertion);

            int deleteIndex =
                    readBoundedIndex(scanner, "Enter the index at which element is to be deleted", 0,
                            arrayAfterInsertion.length - 1);
            int[] arrayAfterDeletion = deleteElement(arrayAfterInsertion, deleteIndex);
            printArray("Array after deletion:", arrayAfterDeletion);
        }
    }

    private static int[] readArray(Scanner scanner) {
        int size = readPositiveInt(scanner, "Enter the size of the array");

        int[] array = new int[size];
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < size; i++) {
            array[i] = readInt(scanner, "Element " + (i + 1) + ":");
        }
        return array;
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // clear invalid token
            }
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Value must be positive. Please try again.");
        }
    }

    private static int readBoundedIndex(Scanner scanner, String prompt, int minInclusive, int maxInclusive) {
        while (true) {
            int index = readInt(scanner,
                    String.format("%s (between %d and %d):", prompt, minInclusive, maxInclusive));
            if (index >= minInclusive && index <= maxInclusive) {
                return index;
            }
            System.out.printf("Index out of bounds. Please enter a value between %d and %d.%n", minInclusive,
                    maxInclusive);
        }
    }

    private static int[] insertElement(int[] originalArray, int insertIndex, int elementToInsert) {
        int[] result = new int[originalArray.length + 1];

        for (int i = 0, j = 0; i < result.length; i++) {
            if (i == insertIndex) {
                result[i] = elementToInsert;
            } else {
                result[i] = originalArray[j++];
            }
        }
        return result;
    }

    private static int[] deleteElement(int[] array, int deleteIndex) {
        int[] result = new int[array.length - 1];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i == deleteIndex) {
                continue;
            }
            result[j++] = array[i];
        }
        return result;
    }

    private static void printArray(String message, int[] array) {
        System.out.println(message);
        for (int value : array) {
            System.out.println(value);
        }
    }
}