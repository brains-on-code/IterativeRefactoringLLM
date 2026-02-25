package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * FibonacciSearch is a search algorithm that finds the position of a target value in
 * a sorted array using Fibonacci numbers.
 *
 * <p>
 * Time complexity: O(log n) <br>
 * Space complexity: O(1)
 * </p>
 *
 * <p>
 * Note: This algorithm requires that the input array be sorted.
 * </p>
 */
public class FibonacciSearch implements SearchAlgorithm {

    /**
     * Finds the index of the specified key in a sorted array using Fibonacci search.
     *
     * @param array the sorted array to search
     * @param key   the element to search for
     * @param <T>   the type of the elements in the array, which must be comparable
     * @return the index of the key if found, otherwise -1
     * @throws IllegalArgumentException if the input array is empty, not sorted, or if the key is null
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        int fibM2 = 0;              // F(m-2)
        int fibM1 = 1;              // F(m-1)
        int fibM = fibM1 + fibM2;   // F(m)

        // Find the smallest Fibonacci number greater than or equal to n
        while (fibM < n) {
            fibM2 = fibM1;
            fibM1 = fibM;
            fibM = fibM1 + fibM2;
        }

        int offset = -1;

        // While there are elements to be inspected
        while (fibM > 1) {
            int i = Math.min(offset + fibM2, n - 1);
            int cmp = array[i].compareTo(key);

            if (cmp < 0) {
                // Move the window to the right: eliminate elements up to index i
                fibM = fibM1;
                fibM1 = fibM2;
                fibM2 = fibM - fibM1;
                offset = i;
            } else if (cmp > 0) {
                // Move the window to the left: eliminate elements after index i
                fibM = fibM2;
                fibM1 = fibM1 - fibM2;
                fibM2 = fibM - fibM1;
            } else {
                return i;
            }
        }

        // Check if the last remaining element matches the key
        if (fibM1 == 1 && offset + 1 < n && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private <T extends Comparable<T>> void validateInput(T[] array, T key) {
        if (array == null || array.length == 0) {
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