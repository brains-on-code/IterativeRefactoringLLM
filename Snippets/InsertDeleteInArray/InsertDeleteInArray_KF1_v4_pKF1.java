package com.thealgorithms.others;

import java.util.Scanner;

public final class ArrayInsertionDeletion {

    private ArrayInsertionDeletion() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int arraySize = scanner.nextInt();
            int[] array = new int[arraySize];

            for (int index = 0; index < arraySize; index++) {
                System.out.println("Enter the element");
                array[index] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertionIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int valueToInsert = scanner.nextInt();

            int updatedSizeAfterInsertion = arraySize + 1;
            int[] arrayWithInsertion = new int[updatedSizeAfterInsertion];

            for (int index = 0; index < updatedSizeAfterInsertion; index++) {
                if (index <= insertionIndex) {
                    arrayWithInsertion[index] = array[index];
                } else {
                    arrayWithInsertion[index] = array[index - 1];
                }
            }
            arrayWithInsertion[insertionIndex] = valueToInsert;

            for (int value : arrayWithInsertion) {
                System.out.println(value);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deletionIndex = scanner.nextInt();

            for (int index = deletionIndex; index < updatedSizeAfterInsertion - 1; index++) {
                arrayWithInsertion[index] = arrayWithInsertion[index + 1];
            }

            for (int index = 0; index < updatedSizeAfterInsertion - 1; index++) {
                System.out.println(arrayWithInsertion[index]);
            }
        }
    }
}