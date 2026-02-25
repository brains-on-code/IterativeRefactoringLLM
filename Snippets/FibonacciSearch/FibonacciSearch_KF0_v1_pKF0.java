package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * FibonacciSearch is a search algorithm that finds the position of a target value in
 * a sorted array using Fibonacci numbers.
 *
 * <p>
 * The time complexity for this search algorithm is O(log n).
 * The space complexity for this search algorithm is O(1).
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
     * @param array The sorted array to search.
     * @param key   The element to search for.
     * @param <T>   The type of the elements in the array, which must be comparable.
     * @throws IllegalArgumentException if the input array is not sorted or empty, or if the key is null.
     * @return The index of the key if found, otherwise -1.
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        int fibMinusTwo = 0; // (m-2)'th Fibonacci number
        int fibMinusOne = 1; // (m-1)'th Fibonacci number
        int fibM = fibMinusOne + fibMinusTwo; // m'th Fibonacci number

        while (fibM < n) {
            fibMinusTwo = fibMinusOne;
            fibMinusOne = fibM;
            fibM = fibMinusOne + fibMinusTwo;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = Math.min(offset + fibMinusTwo, n - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                fibM = fibMinusOne;
                fibMinusOne = fibMinusTwo;
                fibMinusTwo = fibM - fibMinusOne;
                offset = index;
            } else if (comparison > 0) {
                fibM = fibMinusTwo;
                fibMinusOne = fibMinusOne - fibMinusTwo;
                fibMinusTwo = fibM - fibMinusOne;
            } else {
                return index;
            }
        }

        int lastIndex = offset + 1;
        if (fibMinusOne == 1 && lastIndex < n && array[lastIndex].compareTo(key) == 0) {
            return lastIndex;
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