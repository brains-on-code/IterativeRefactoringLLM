package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of binary search on a sorted array.
 *
 * @param <T> the type of elements in the array, which must be comparable
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    /**
     * Searches for the specified key in the given sorted array using
     * iterative binary search.
     *
     * @param array the sorted array to search
     * @param key   the value to search for
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the index of {@code key} if found; {@code -1} otherwise
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}