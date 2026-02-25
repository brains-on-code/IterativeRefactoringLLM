package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search implementation.
 */
public final class BinarySearch implements SearchAlgorithm {

    /**
     * Performs binary search on a sorted array.
     *
     * @param array the sorted array to search
     * @param target the value to search for
     * @param <T> the type of elements in the array
     * @return the index of the target if found; otherwise -1
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int middle = (low + high) >>> 1;
            int comparison = target.compareTo(array[middle]);

            if (comparison == 0) {
                return middle;
            } else if (comparison < 0) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }

        return -1;
    }
}