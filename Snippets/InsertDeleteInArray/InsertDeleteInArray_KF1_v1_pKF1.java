package com.thealgorithms.others;

import java.util.Scanner;

public final class ArrayInsertionDeletion {
    private ArrayInsertionDeletion() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int arraySize = scanner.nextInt();
            int[] originalArray = new int[arraySize];

            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter the element");
                originalArray[i] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newSizeAfterInsert = arraySize + 1;
            int[] arrayAfterInsert = new int[newSizeAfterInsert];

            for (int i = 0; i < newSizeAfterInsert; i++) {
                if (i <= insertIndex) {
                    arrayAfterInsert[i] = originalArray[i];
                } else {
                    arrayAfterInsert[i] = originalArray[i - 1];
                }
            }
            arrayAfterInsert[insertIndex] = elementToInsert;

            for (int i = 0; i < newSizeAfterInsert; i++) {
                System.out.println(arrayAfterInsert[i]);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int i = deleteIndex; i < newSizeAfterInsert - 1; i++) {
                arrayAfterInsert[i] = arrayAfterInsert[i + 1];
            }

            for (int i = 0; i < newSizeAfterInsert - 1; i++) {
                System.out.println(arrayAfterInsert[i]);
            }
        }
    }
}