package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search implementation.
 *
 * <p>Searches for a target element in a sorted array using the binary search
 * algorithm. The array must be sorted in ascending order according to the
 * natural ordering of its elements.
 */
public final class BinarySearch implements SearchAlgorithm {

    /**
     * Searches for a key in a sorted array using binary search.
     *
     * @param <T>   the type of elements in the array; must be {@link Comparable}
     * @param array the sorted array to search (must be in ascending order)
     * @param key   the value to search for
     * @return the index of {@code key} in {@code array} if found; otherwise {@code -1}
     * @throws NullPointerException if {@code array} or {@code key} is {@code null}
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            // Use unsigned right shift to avoid potential integer overflow
            int mid = (left + right) >>> 1;
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                right = mid - 1; // Key is in the left half
            } else {
                left = mid + 1; // Key is in the right half
            }
        }

        return -1;
    }
}