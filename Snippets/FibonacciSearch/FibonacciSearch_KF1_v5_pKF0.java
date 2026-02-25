package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Fibonacci Search implementation.
 *
 * Searches for a given key in a sorted array using the Fibonacci Search algorithm.
 */
public class FibonacciSearch implements SearchAlgorithm {

    /**
     * Performs Fibonacci Search on a sorted array.
     *
     * @param array the sorted array to search
     * @param key   the value to search for
     * @param <T>   the type of elements in the array, must be Comparable
     * @return the index of the key if found, otherwise -1
     * @throws IllegalArgumentException if the array is empty, unsorted, or if the key is null
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int length = array.length;

        // Initialize Fibonacci numbers
        int fibMMinus2 = 0; // (m-2)'th Fibonacci number
        int fibMMinus1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibMMinus1 + fibMMinus2; // m'th Fibonacci number

        // Find the smallest Fibonacci number greater than or equal to length
        while (fibM < length) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus1 + fibMMinus2;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = calculateIndex(offset, fibMMinus2, length);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                offset = index;
                int previousFibMMinus1 = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - previousFibMMinus1;
                fibM = previousFibMMinus1;
            } else if (comparison > 0) {
                int previousFibMMinus2 = fibMMinus2;
                fibMMinus2 = fibMMinus1 - fibMMinus2;
                fibMMinus1 = previousFibMMinus2;
                fibM = fibMMinus2 + fibMMinus1;
            } else {
                return index;
            }
        }

        return isLastElementMatch(array, key, fibMMinus1, offset, length)
            ? offset + 1
            : -1;
    }

    private int calculateIndex(int offset, int fibMMinus2, int length) {
        return Math.min(offset + fibMMinus2, length - 1);
    }

    private <T extends Comparable<T>> boolean isLastElementMatch(
        T[] array,
        T key,
        int fibMMinus1,
        int offset,
        int length
    ) {
        int lastIndex = offset + 1;
        return fibMMinus1 == 1
            && lastIndex < length
            && array[lastIndex].compareTo(key) == 0;
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