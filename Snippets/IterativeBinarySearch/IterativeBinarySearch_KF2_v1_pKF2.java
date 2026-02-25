package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of binary search.
 *
 * <p>Returns the index of the key in the array if found, otherwise -1.</p>
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        // Continue searching while the search space is valid
        while (left <= right) {
            // Compute the middle index without risk of integer overflow
            int mid = (left + right) >>> 1;
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                // Key found at index mid
                return mid;
            } else if (comparison < 0) {
                // Key is smaller than the middle element; search the left half
                right = mid - 1;
            } else {
                // Key is larger than the middle element; search the right half
                left = mid + 1;
            }
        }

        // Key not found
        return -1;
    }
}