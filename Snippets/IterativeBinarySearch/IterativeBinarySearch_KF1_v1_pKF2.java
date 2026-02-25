package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search implementation.
 *
 * <p>Searches for a target element in a sorted array using the binary search
 * algorithm. The array must be sorted in ascending order according to the
 * natural ordering of its elements.
 */
public final class Class1 implements SearchAlgorithm {

    /**
     * Performs binary search on a sorted array.
     *
     * @param <T>   the type of elements in the array; must be {@link Comparable}
     * @param array the sorted array to search
     * @param key   the value to search for
     * @return the index of {@code key} in {@code array} if found; otherwise {@code -1}
     */
    @Override
    public <T extends Comparable<T>> int method1(T[] array, T key) {
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