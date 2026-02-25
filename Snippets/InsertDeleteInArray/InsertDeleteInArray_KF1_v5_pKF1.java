package com.thealgorithms.others;

import java.util.Scanner;

public final class ArrayInsertionDeletion {

    private ArrayInsertionDeletion() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int originalArraySize = scanner.nextInt();
            int[] originalArray = new int[originalArraySize];

            for (int i = 0; i < originalArraySize; i++) {
                System.out.println("Enter the element");
                originalArray[i] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertionIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newArraySizeAfterInsertion = originalArraySize + 1;
            int[] arrayAfterInsertion = new int[newArraySizeAfterInsertion];

            for (int i = 0; i < newArraySizeAfterInsertion; i++) {
                if (i <= insertionIndex) {
                    arrayAfterInsertion[i] = originalArray[i];
                } else {
                    arrayAfterInsertion[i] = originalArray[i - 1];
                }
            }
            arrayAfterInsertion[insertionIndex] = elementToInsert;

            for (int element : arrayAfterInsertion) {
                System.out.println(element);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deletionIndex = scanner.nextInt();

            for (int i = deletionIndex; i < newArraySizeAfterInsertion - 1; i++) {
                arrayAfterInsertion[i] = arrayAfterInsertion[i + 1];
            }

            for (int i = 0; i < newArraySizeAfterInsertion - 1; i++) {
                System.out.println(arrayAfterInsertion[i]);
            }
        }
    }
}