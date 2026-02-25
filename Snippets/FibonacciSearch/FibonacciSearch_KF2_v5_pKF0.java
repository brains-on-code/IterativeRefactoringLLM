package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int length = array.length;

        int fibM2 = 0; // F(m-2)
        int fibM1 = 1; // F(m-1)
        int fibM = fibM1 + fibM2; // F(m)

        // Find the smallest Fibonacci number greater than or equal to length
        while (fibM < length) {
            fibM2 = fibM1;
            fibM1 = fibM;
            fibM = fibM1 + fibM2;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = Math.min(offset + fibM2, length - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                // Move three Fibonacci numbers down
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

        int lastIndex = offset + 1;
        if (fibM1 == 1 && lastIndex < length && array[lastIndex].compareTo(key) == 0) {
            return lastIndex;
        }

        return -1;
    }

    private <T extends Comparable<T>> void validateInput(T[] array, T key) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array must not be null or empty.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
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