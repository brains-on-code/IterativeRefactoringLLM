package com.thealgorithms.others;

import java.util.Scanner;

public final class ArrayInsertionDeletion {

    private ArrayInsertionDeletion() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int initialArraySize = scanner.nextInt();
            int[] array = new int[initialArraySize];

            for (int index = 0; index < initialArraySize; index++) {
                System.out.println("Enter the element");
                array[index] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertionIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int valueToInsert = scanner.nextInt();

            int arraySizeAfterInsertion = initialArraySize + 1;
            int[] arrayAfterInsertion = new int[arraySizeAfterInsertion];

            for (int index = 0; index < arraySizeAfterInsertion; index++) {
                if (index <= insertionIndex) {
                    arrayAfterInsertion[index] = array[index];
                } else {
                    arrayAfterInsertion[index] = array[index - 1];
                }
            }
            arrayAfterInsertion[insertionIndex] = valueToInsert;

            for (int index = 0; index < arraySizeAfterInsertion; index++) {
                System.out.println(arrayAfterInsertion[index]);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deletionIndex = scanner.nextInt();

            for (int index = deletionIndex; index < arraySizeAfterInsertion - 1; index++) {
                arrayAfterInsertion[index] = arrayAfterInsertion[index + 1];
            }

            for (int index = 0; index < arraySizeAfterInsertion - 1; index++) {
                System.out.println(arrayAfterInsertion[index]);
            }
        }
    }
}