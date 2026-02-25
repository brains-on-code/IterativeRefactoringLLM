package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            // Read initial array size and elements
            System.out.println("Enter the size of the array");
            int size = scanner.nextInt();
            int[] originalArray = new int[size];

            for (int i = 0; i < size; i++) {
                System.out.println("Enter the element");
                originalArray[i] = scanner.nextInt();
            }

            // Insert operation
            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();

            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int[] arrayAfterInsert = new int[size + 1];

            for (int i = 0; i < arrayAfterInsert.length; i++) {
                if (i < insertIndex) {
                    arrayAfterInsert[i] = originalArray[i];
                } else if (i == insertIndex) {
                    arrayAfterInsert[i] = elementToInsert;
                } else {
                    arrayAfterInsert[i] = originalArray[i - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int value : arrayAfterInsert) {
                System.out.println(value);
            }

            // Delete operation
            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int i = deleteIndex; i < arrayAfterInsert.length - 1; i++) {
                arrayAfterInsert[i] = arrayAfterInsert[i + 1];
            }

            System.out.println("Array after deletion:");
            for (int i = 0; i < arrayAfterInsert.length - 1; i++) {
                System.out.println(arrayAfterInsert[i]);
            }
        }
    }
}