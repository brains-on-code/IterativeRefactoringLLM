package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of the binary search algorithm.
 *
 * <p>Requirements:
 * <ul>
 *   <li>The input array must be sorted in ascending order.</li>
 *   <li>The elements must implement {@link Comparable}.</li>
 * </ul>
 *
 * <p>Complexity:
 * <ul>
 *   <li>Worst-case time: O(log n)</li>
 *   <li>Best-case time: O(1)</li>
 *   <li>Average time: O(log n)</li>
 *   <li>Worst-case space: O(1)</li>
 * </ul>
 *
 * @author Gabriele La Greca : https://github.com/thegabriele97
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 * @see SearchAlgorithm
 * @see BinarySearch
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    /**
     * Performs an iterative binary search on a sorted array.
     *
     * @param array a sorted array
     * @param key   the key to search for in the array
     * @param <T>   the type of elements in the array
     * @return the index of {@code key} in {@code array}, or -1 if not found
     * @throws IllegalArgumentException if {@code array} is null
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateArray(array);
        return binarySearch(array, key);
    }

    private <T extends Comparable<T>> int binarySearch(T[] array, T key) {
        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + ((rightIndex - leftIndex) >>> 1);
            T middleValue = array[middleIndex];
            int comparisonResult = key.compareTo(middleValue);

            if (comparisonResult == 0) {
                return middleIndex;
            }

            if (comparisonResult < 0) {
                rightIndex = middleIndex - 1;
            } else {
                leftIndex = middleIndex + 1;
            }
        }

        return -1;
    }

    private void validateArray(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
    }
}