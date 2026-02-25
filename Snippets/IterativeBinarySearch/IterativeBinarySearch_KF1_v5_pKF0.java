package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Binary search implementation.
 */
public final class BinarySearch implements SearchAlgorithm {

    private static final int NOT_FOUND = -1;

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
        if (isInvalidInput(array, key)) {
            return NOT_FOUND;
        }

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = calculateMidpoint(low, high);
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                high = mid - 1;
                continue;
            }

            low = mid + 1;
        }

        return NOT_FOUND;
    }

    private static boolean isInvalidInput(Object[] array, Object key) {
        return array == null || key == null || array.length == 0;
    }

    private static int calculateMidpoint(int low, int high) {
        return low + ((high - low) >>> 1);
    }
}