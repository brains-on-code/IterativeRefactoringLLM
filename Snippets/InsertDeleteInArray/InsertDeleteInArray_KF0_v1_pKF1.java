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

            // Enter the initial elements
            for (int index = 0; index < originalSize; index++) {
                System.out.println("Enter the element");
                originalArray[index] = scanner.nextInt();
            }

            // Insert a new element (create a new array)
            System.out.println("Enter the index at which the element should be inserted");
            int insertIndex = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int elementToInsert = scanner.nextInt();

            int newSize = originalSize + 1;
            int[] expandedArray = new int[newSize];

            for (int index = 0; index < newSize; index++) {
                if (index <= insertIndex) {
                    expandedArray[index] = originalArray[index];
                } else {
                    expandedArray[index] = originalArray[index - 1];
                }
            }
            expandedArray[insertIndex] = elementToInsert;

            for (int value : expandedArray) {
                System.out.println(value);
            }

            // Delete an element at a given index
            System.out.println("Enter the index at which element is to be deleted");
            int deleteIndex = scanner.nextInt();

            for (int index = deleteIndex; index < newSize - 1; index++) {
                expandedArray[index] = expandedArray[index + 1];
            }

            for (int index = 0; index < newSize - 1; index++) {
                System.out.println(expandedArray[index]);
            }
        }
    }
}