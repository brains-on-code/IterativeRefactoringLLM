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

        int fibTwoStepsBack = 0; // (m-2)'th Fibonacci number
        int fibOneStepBack = 1; // (m-1)'th Fibonacci number
        int fibCurrent = fibOneStepBack + fibTwoStepsBack; // m'th Fibonacci number
        int arrayLength = array.length;

        while (fibCurrent < arrayLength) {
            fibTwoStepsBack = fibOneStepBack;
            fibOneStepBack = fibCurrent;
            fibCurrent = fibOneStepBack + fibTwoStepsBack;
        }

        int offset = -1;

        while (fibCurrent > 1) {
            int probeIndex = Math.min(offset + fibTwoStepsBack, arrayLength - 1);
            int comparisonResult = array[probeIndex].compareTo(key);

            if (comparisonResult < 0) {
                fibCurrent = fibOneStepBack;
                fibOneStepBack = fibTwoStepsBack;
                fibTwoStepsBack = fibCurrent - fibOneStepBack;
                offset = probeIndex;
            } else if (comparisonResult > 0) {
                fibCurrent = fibTwoStepsBack;
                fibOneStepBack = fibOneStepBack - fibTwoStepsBack;
                fibTwoStepsBack = fibCurrent - fibOneStepBack;
            } else {
                return probeIndex;
            }
        }

        int lastCandidateIndex = offset + 1;
        if (fibOneStepBack == 1
                && lastCandidateIndex < arrayLength
                && array[lastCandidateIndex].compareTo(key) == 0) {
            return lastCandidateIndex;
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