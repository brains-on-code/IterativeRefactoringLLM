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
        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex <= rightIndex) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            int comparisonResult = target.compareTo(array[middleIndex]);

            if (comparisonResult == 0) {
                return middleIndex;
            } else if (comparisonResult < 0) {
                rightIndex = middleIndex - 1;
            } else {
                leftIndex = middleIndex + 1;
            }
        }

        return -1;
    }
}