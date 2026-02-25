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

            for (int i = 0; i < arraySize; i++) {
                System.out.println("Enter the element");
                array[i] = scanner.nextInt();
            }

            System.out.println("Enter the index at which the element should be inserted");
            int insertPosition = scanner.nextInt();
            System.out.println("Enter the element to be inserted");
            int valueToInsert = scanner.nextInt();

            int newArraySize = arraySize + 1;
            int[] expandedArray = new int[newArraySize];

            for (int i = 0; i < newArraySize; i++) {
                if (i <= insertPosition) {
                    expandedArray[i] = array[i];
                } else {
                    expandedArray[i] = array[i - 1];
                }
            }
            expandedArray[insertPosition] = valueToInsert;

            for (int i = 0; i < newArraySize; i++) {
                System.out.println(expandedArray[i]);
            }

            System.out.println("Enter the index at which element is to be deleted");
            int deletePosition = scanner.nextInt();

            for (int i = deletePosition; i < newArraySize - 1; i++) {
                expandedArray[i] = expandedArray[i + 1];
            }

            for (int i = 0; i < newArraySize - 1; i++) {
                System.out.println(expandedArray[i]);
            }
        }
    }
}