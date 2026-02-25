package com.thealgorithms.others;

import java.util.Scanner;

public final class InsertDeleteInArray {

    private InsertDeleteInArray() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int arraySize = scanner.nextInt();
            int[] array = new int[arraySize];

            // Read initial elements
            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter the element");
                array[i] = scanner.nextInt();
            }

            // Insert a new element (create a new array)
            System.out.println("Enter the index at which the element should be inserted");
            int insertPosition = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int valueToInsert = scanner.nextInt();

            int expandedSize = arraySize + 1;
            int[] expandedArray = new int[expandedSize];

            for (int i = 0; i < expandedSize; i++) {
                if (i < insertPosition) {
                    expandedArray[i] = array[i];
                } else if (i == insertPosition) {
                    expandedArray[i] = valueToInsert;
                } else {
                    expandedArray[i] = array[i - 1];
                }
            }

            System.out.println("Array after insertion:");
            for (int value : expandedArray) {
                System.out.println(value);
            }

            // Delete an element at a given index
            System.out.println("Enter the index at which element is to be deleted");
            int deletePosition = scanner.nextInt();

            for (int i = deletePosition; i < expandedSize - 1; i++) {
                expandedArray[i] = expandedArray[i + 1];
            }

            System.out.println("Array after deletion:");
            for (int i = 0; i < expandedSize - 1; i++) {
                System.out.println(expandedArray[i]);
            }
        }
    }
}