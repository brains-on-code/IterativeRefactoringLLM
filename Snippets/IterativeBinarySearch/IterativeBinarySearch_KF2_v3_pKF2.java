package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of binary search.
 *
 * <p>Searches for {@code key} in a sorted array and returns its index if found,
 * otherwise returns {@code -1}.</p>
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    /**
     * Performs an iterative binary search on a sorted array.
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

        // Continue searching while the search interval is valid
        while (left <= right) {
            // Use unsigned right shift to avoid potential overflow
            int mid = (left + right) >>> 1;
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                // Key found at index mid
                return mid;
            }

            if (comparison < 0) {
                // Key is in the left half (if present)
                right = mid - 1;
            } else {
                // Key is in the right half (if present)
                left = mid + 1;
            }
        }

        // Key not found
        return -1;
    }
}