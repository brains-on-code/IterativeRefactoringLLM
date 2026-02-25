package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        // Initialize Fibonacci numbers: fibM = fib(k), fibMm1 = fib(k-1), fibMm2 = fib(k-2)
        int fibMm2 = 0; // (m-2)'th Fibonacci number
        int fibMm1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibMm1 + fibMm2; // m'th Fibonacci number

        // Find the smallest Fibonacci number greater than or equal to n
        while (fibM < n) {
            fibMm2 = fibMm1;
            fibMm1 = fibM;
            fibM = fibMm1 + fibMm2;
        }

        // Marks the eliminated range from the front
        int offset = -1;

        // While there are elements to be inspected
        while (fibM > 1) {
            int i = Math.min(offset + fibMm2, n - 1);

            int comparison = array[i].compareTo(key);

            // If key is greater than the value at index i, cut the subarray from offset to i
            if (comparison < 0) {
                fibM = fibMm1;
                fibMm1 = fibMm2;
                fibMm2 = fibM - fibMm1;
                offset = i;
            }
            // If key is smaller than the value at index i, cut the subarray after i+1
            else if (comparison > 0) {
                fibM = fibMm2;
                fibMm1 = fibMm1 - fibMm2;
                fibMm2 = fibM - fibMm1;
            }
            // Element found
            else {
                return i;
            }
        }

        // Check if the last possible element is the key
        if (fibMm1 == 1 && offset + 1 < n && array[offset + 1].compareTo(key) == 0) {
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