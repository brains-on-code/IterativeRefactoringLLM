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
     * Performs binary search on a sorted array.
     *
     * @param <T>   the type of elements in the array; must be {@link Comparable}
     * @param array the sorted array to search
     * @param key   the value to search for
     * @return the index of {@code key} in {@code array} if found; otherwise {@code -1}
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        // Continue searching while the search interval is valid
        while (left <= right) {
            // Compute the middle index without risk of integer overflow
            int mid = (left + right) >>> 1;

            // Compare the key with the middle element
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                // Key found at index mid
                return mid;
            } else if (comparison < 0) {
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