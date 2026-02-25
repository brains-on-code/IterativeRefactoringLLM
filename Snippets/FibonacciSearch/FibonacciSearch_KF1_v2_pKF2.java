package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Fibonacci Search implementation.
 *
 * <p>Searches for a given key in a sorted array using the Fibonacci Search algorithm.
 */
public class Class1 implements SearchAlgorithm {

    /**
     * Performs Fibonacci Search on a sorted array.
     *
     * @param <T>   the type of elements in the array; must be {@link Comparable}
     * @param array the sorted array to search in (must not be empty)
     * @param key   the value to search for (must not be {@code null})
     * @return the index of {@code key} in {@code array} if found; otherwise {@code -1}
     * @throws IllegalArgumentException if {@code array} is empty, not sorted, or {@code key} is {@code null}
     */
    @Override
    public <T extends Comparable<T>> int method1(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        // Initialize Fibonacci numbers: fibM = fibMMinus1 + fibMMinus2
        int fibMMinus2 = 0;
        int fibMMinus1 = 1;
        int fibM = fibMMinus1 + fibMMinus2;

        // Find the smallest Fibonacci number greater than or equal to n
        while (fibM < n) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus1 + fibMMinus2;
        }

        // Marks the eliminated range from the front
        int offset = -1;

        // While there are elements to be inspected
        while (fibM > 1) {
            int i = Math.min(offset + fibMMinus2, n - 1);
            int cmp = array[i].compareTo(key);

            if (cmp < 0) {
                // Move the three Fibonacci variables one down
                fibM = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
                offset = i;
            } else if (cmp > 0) {
                // Move the three Fibonacci variables two down
                fibM = fibMMinus2;
                fibMMinus1 = fibMMinus1 - fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
            } else {
                return i;
            }
        }

        // Check if the last possible element matches the key
        if (fibMMinus1 == 1 && offset + 1 < n && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private <T extends Comparable<T>> void validateInput(T[] array, T key) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty.");
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