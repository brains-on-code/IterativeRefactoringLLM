package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Fibonacci Search implementation.
 *
 * Searches for a given key in a sorted array using the Fibonacci Search algorithm.
 */
public class Class1 implements SearchAlgorithm {

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
    public <T extends Comparable<T>> int method1(T[] array, T key) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }

        int fibMMinus2 = 0; // (m-2)'th Fibonacci number
        int fibMMinus1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibMMinus1 + fibMMinus2; // m'th Fibonacci number
        int length = array.length;

        // Find the smallest Fibonacci number greater than or equal to length
        while (fibM < length) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus1 + fibMMinus2;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = Math.min(offset + fibMMinus2, length - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                fibM = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
                offset = index;
            } else if (comparison > 0) {
                fibM = fibMMinus2;
                fibMMinus1 = fibMMinus1 - fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
            } else {
                return index;
            }
        }

        if (fibMMinus1 == 1 && offset + 1 < length && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private boolean isSorted(Comparable[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1].compareTo(array[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}