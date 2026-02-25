package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search implementation.
 */
public final class BinarySearch implements SearchAlgorithm {

    /**
     * Performs binary search on a sorted array.
     *
     * @param <T>   the type of elements, must be comparable
     * @param array the sorted array to search
     * @param key   the value to search for
     * @return index of {@code key} if found; otherwise {@code -1}
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (array == null || key == null || array.length == 0) {
            return -1;
        }

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midValue = array[mid];
            int comparison = key.compareTo(midValue);

            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }
}