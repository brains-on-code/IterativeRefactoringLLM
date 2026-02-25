package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int originalLength = scanner.nextInt();
            int[] originalArray = new int[originalLength];

            for (int index = 0; index < originalLength; index++) {
                System.out.println("Enter the element");
                originalArray[index] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int expandedLength = originalLength + 1;
            int[] expandedArray = new int[expandedLength];

            for (int index = 0; index < expandedLength; index++) {
                if (index <= insertIndex) {
                    expandedArray[index] = originalArray[index];
                } else {
                    expandedArray[index] = originalArray[index - 1];
                }
            }
            expandedArray[insertIndex] = elementToInsert;

            for (int index = 0; index < expandedLength; index++) {
                System.out.println(expandedArray[index]);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int index = deleteIndex; index < expandedLength - 1; index++) {
                expandedArray[index] = expandedArray[index + 1];
            }

            for (int index = 0; index < expandedLength - 1; index++) {
                System.out.println(expandedArray[index]);
            }
        }
    }
}