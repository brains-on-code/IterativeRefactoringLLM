package com.thealgorithms.others;

import java.util.Scanner;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] originalArray = readArray(scanner);
            int[] arrayAfterInsertion = insertElement(scanner, originalArray);
            printArray("Array after insertion:", arrayAfterInsertion);

            int[] arrayAfterDeletion = deleteElement(scanner, arrayAfterInsertion);
            printArray("Array after deletion:", arrayAfterDeletion);
        }
    }

    private static int[] readArray(Scanner scanner) {
        System.out.println("Enter the size of the array");
        int size = scanner.nextInt();

        int[] array = new int[size];
        System.out.println("Enter the elements of the array");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static int[] insertElement(Scanner scanner, int[] originalArray) {
        System.out.println("Enter the index at which the element should be inserted");
        int insertIndex = scanner.nextInt();

        System.out.println("Enter the element to be inserted");
        int elementToInsert = scanner.nextInt();

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

    private static int[] deleteElement(Scanner scanner, int[] array) {
        System.out.println("Enter the index at which element is to be deleted");
        int deleteIndex = scanner.nextInt();

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