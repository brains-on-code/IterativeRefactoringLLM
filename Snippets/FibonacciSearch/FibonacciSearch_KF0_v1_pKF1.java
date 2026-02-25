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
     * @param key The element to search for.
     * @param <T> The type of the elements in the array, which must be comparable.
     * @throws IllegalArgumentException if the input array is not sorted or empty, or if the key is null.
     * @return The index of the key if found, otherwise -1.
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }

        int previousFib = 1;
        int currentFib = 0;
        int fibNumber = previousFib + currentFib;
        int arrayLength = array.length;

        while (fibNumber < arrayLength) {
            currentFib = previousFib;
            previousFib = fibNumber;
            fibNumber = currentFib + previousFib;
        }

        int offset = -1;

        while (fibNumber > 1) {
            int index = Math.min(offset + currentFib, arrayLength - 1);
            int comparisonResult = array[index].compareTo(key);

            if (comparisonResult < 0) {
                fibNumber = previousFib;
                previousFib = currentFib;
                currentFib = fibNumber - previousFib;
                offset = index;
            } else if (comparisonResult > 0) {
                fibNumber = currentFib;
                previousFib = previousFib - currentFib;
                currentFib = fibNumber - previousFib;
            } else {
                return index;
            }
        }

        if (previousFib == 1 && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private boolean isSorted(Comparable[] array) {
        for (int index = 1; index < array.length; index++) {
            if (array[index - 1].compareTo(array[index]) > 0) {
                return false;
            }
        }
        return true;
    }
}