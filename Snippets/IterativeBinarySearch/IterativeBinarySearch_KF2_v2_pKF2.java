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

        while (left <= right) {
            int mid = (left + right) >>> 1;
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}