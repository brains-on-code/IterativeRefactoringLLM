package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search is one of the most popular algorithms. This class represents
 * the iterative version of {@link BinarySearch}. Iterative binary search is
 * likely to have lower constant factors because it doesn't involve the overhead
 * of manipulating the call stack. But in Java the recursive version can be
 * optimized by the compiler to this version.
 *
 * <p>
 * Worst-case performance O(log n) Best-case performance O(1) Average
 * performance O(log n) Worst-case space complexity O(1)
 *
 * @author Gabriele La Greca : https://github.com/thegabriele97
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 * @see SearchAlgorithm
 * @see BinarySearch
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    /**
     * This method implements an iterative version of the binary search algorithm.
     *
     * @param array a sorted array
     * @param key   the key to search in array
     * @return the index of key in the array or -1 if not found
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex <= rightIndex) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            int comparisonResult = key.compareTo(array[middleIndex]);

            if (comparisonResult == 0) {
                return middleIndex;
            } else if (comparisonResult < 0) {
                rightIndex = middleIndex - 1;
            } else {
                leftIndex = middleIndex + 1;
            }
        }

        return -1;
    }
}