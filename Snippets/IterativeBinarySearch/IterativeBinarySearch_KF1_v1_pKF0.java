package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search implementation.
 */
public final class Class1 implements SearchAlgorithm {

    /**
     * Performs binary search on a sorted array.
     *
     * @param <T>   the type of elements, must be comparable
     * @param array the sorted array to search
     * @param key   the value to search for
     * @return index of {@code key} if found; otherwise {@code -1}
     */
    @Override
    public <T extends Comparable<T>> int method1(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int middle = (left + right) >>> 1;
            int comparison = key.compareTo(array[middle]);

            if (comparison == 0) {
                return middle;
            } else if (comparison < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return -1;
    }
}