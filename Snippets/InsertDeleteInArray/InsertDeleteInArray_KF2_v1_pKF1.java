package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int originalSize = scanner.nextInt();
            int[] originalArray = new int[originalSize];

            for (int index = 0; index < originalSize; index++) {
                System.out.println("Enter the element");
                originalArray[index] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newSizeAfterInsert = originalSize + 1;
            int[] arrayAfterInsert = new int[newSizeAfterInsert];

            for (int index = 0; index < newSizeAfterInsert; index++) {
                if (index <= insertIndex) {
                    arrayAfterInsert[index] = originalArray[index];
                } else {
                    arrayAfterInsert[index] = originalArray[index - 1];
                }
            }
            arrayAfterInsert[insertIndex] = elementToInsert;

            for (int index = 0; index < newSizeAfterInsert; index++) {
                System.out.println(arrayAfterInsert[index]);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int index = deleteIndex; index < newSizeAfterInsert - 1; index++) {
                arrayAfterInsert[index] = arrayAfterInsert[index + 1];
            }

            for (int index = 0; index < newSizeAfterInsert - 1; index++) {
                System.out.println(arrayAfterInsert[index]);
            }
        }
    }
}