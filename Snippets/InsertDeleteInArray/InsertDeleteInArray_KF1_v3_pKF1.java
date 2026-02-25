package com.thealgorithms.others;

import java.util.Scanner;

public final class ArrayInsertionDeletion {

    private ArrayInsertionDeletion() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int originalSize = scanner.nextInt();
            int[] originalArray = new int[originalSize];

            for (int i = 0; i < originalSize; i++) {
                System.out.println("Enter the element");
                originalArray[i] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertionIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newSizeAfterInsertion = originalSize + 1;
            int[] arrayAfterInsertion = new int[newSizeAfterInsertion];

            for (int i = 0; i < newSizeAfterInsertion; i++) {
                if (i <= insertionIndex) {
                    arrayAfterInsertion[i] = originalArray[i];
                } else {
                    arrayAfterInsertion[i] = originalArray[i - 1];
                }
            }
            arrayAfterInsertion[insertionIndex] = elementToInsert;

            for (int value : arrayAfterInsertion) {
                System.out.println(value);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deletionIndex = scanner.nextInt();

            for (int i = deletionIndex; i < newSizeAfterInsertion - 1; i++) {
                arrayAfterInsertion[i] = arrayAfterInsertion[i + 1];
            }

            for (int i = 0; i < newSizeAfterInsertion - 1; i++) {
                System.out.println(arrayAfterInsertion[i]);
            }
        }
    }
}