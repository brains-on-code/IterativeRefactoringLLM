package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        // Initialize Fibonacci numbers: fibM = fibM1 + fibM2
        int fibM2 = 0; // (m-2)'th Fibonacci number
        int fibM1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibM1 + fibM2; // m'th Fibonacci number

        // Find the smallest Fibonacci number greater than or equal to n
        while (fibM < n) {
            fibM2 = fibM1;
            fibM1 = fibM;
            fibM = fibM1 + fibM2;
        }

        // Marks the eliminated range from the front
        int offset = -1;

        // While there are elements to be inspected
        while (fibM > 1) {
            int index = Math.min(offset + fibM2, n - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                // Move three Fibonacci numbers down: fibM becomes fibM1
                fibM = fibM1;
                fibM1 = fibM2;
                fibM2 = fibM - fibM1;
                offset = index;
            } else if (comparison > 0) {
                // Move two Fibonacci numbers down
                fibM = fibM2;
                fibM1 = fibM1 - fibM2;
                fibM2 = fibM - fibM1;
            } else {
                return index;
            }
        }

        // Check if the last possible element is the key
        if (fibM1 == 1 && offset + 1 < n && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private <T extends Comparable<T>> void validateInput(T[] array, T key) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array must not be null or empty.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
    }

    private <T extends Comparable<T>> boolean isSorted(T[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1].compareTo(array[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}