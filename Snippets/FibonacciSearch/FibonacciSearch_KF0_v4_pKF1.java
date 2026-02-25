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

        int fibMMinus2 = 0; // (m-2)'th Fibonacci number
        int fibMMinus1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibMMinus1 + fibMMinus2; // m'th Fibonacci number
        int length = array.length;

        while (fibM < length) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus1 + fibMMinus2;
        }

        int offset = -1;

        while (fibM > 1) {
            int indexToCompare = Math.min(offset + fibMMinus2, length - 1);
            int comparisonResult = array[indexToCompare].compareTo(key);

            if (comparisonResult < 0) {
                fibM = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
                offset = indexToCompare;
            } else if (comparisonResult > 0) {
                fibM = fibMMinus2;
                fibMMinus1 = fibMMinus1 - fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
            } else {
                return indexToCompare;
            }
        }

        int lastCandidateIndex = offset + 1;
        if (fibMMinus1 == 1
                && lastCandidateIndex < length
                && array[lastCandidateIndex].compareTo(key) == 0) {
            return lastCandidateIndex;
        }

        return -1;
    }

    private boolean isSorted(Comparable[] array) {
        for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
            if (array[currentIndex - 1].compareTo(array[currentIndex]) > 0) {
                return false;
            }
        }
        return true;
    }
}