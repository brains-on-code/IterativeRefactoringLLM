package com.thealgorithms.others;

import java.util.Scanner;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int size = scanner.nextInt();

            int[] originalArray = new int[size];

            // Read array elements from user
            for (int i = 0; i < size; i++) {
                System.out.println("Enter the element");
                originalArray[i] = scanner.nextInt();
            }

            // Insert an element at a specified index
            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();

            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newSize = size + 1;
            int[] extendedArray = new int[newSize];

            for (int i = 0; i < newSize; i++) {
                if (i < insertIndex) {
                    extendedArray[i] = originalArray[i];
                } else if (i == insertIndex) {
                    extendedArray[i] = elementToInsert;
                } else {
                    extendedArray[i] = originalArray[i - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int i = 0; i < newSize; i++) {
                System.out.println(extendedArray[i]);
            }

            // Delete an element at a specified index
            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int i = deleteIndex; i < newSize - 1; i++) {
                extendedArray[i] = extendedArray[i + 1];
            }

            System.out.println("Array after deletion:");
            for (int i = 0; i < newSize - 1; i++) {
                System.out.println(extendedArray[i]);
            }
        }
    }
}