package com.thealgorithms.others;

import java.util.Scanner;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] originalArray = readArray(scanner);

            int insertIndex = readIndex(scanner, "Enter the index at which the element should be inserted");
            int elementToInsert = readInt(scanner, "Enter the element to be inserted");
            int[] arrayAfterInsertion = insertElement(originalArray, insertIndex, elementToInsert);
            printArray("Array after insertion:", arrayAfterInsertion);

            int deleteIndex = readIndex(scanner, "Enter the index at which element is to be deleted");
            int[] arrayAfterDeletion = deleteElement(arrayAfterInsertion, deleteIndex);
            printArray("Array after deletion:", arrayAfterDeletion);
        }
    }

    private static int[] readArray(Scanner scanner) {
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

    private static int[] insertElement(int[] originalArray, int insertIndex, int elementToInsert) {
        int newSize = originalArray.length + 1;
        int[] extendedArray = new int[newSize];

        for (int i = 0, j = 0; i < newSize; i++) {
            if (i == insertIndex) {
                extendedArray[i] = elementToInsert;
            } else {
                extendedArray[i] = originalArray[j++];
            }
        }
        return extendedArray;
    }

    private static int[] deleteElement(int[] array, int deleteIndex) {
        int newSize = array.length - 1;
        int[] resultArray = new int[newSize];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i == deleteIndex) {
                continue;
            }
            resultArray[j++] = array[i];
        }
        return resultArray;
    }

    private static void printArray(String message, int[] array) {
        System.out.println(message);
        for (int value : array) {
            System.out.println(value);
        }
    }
}