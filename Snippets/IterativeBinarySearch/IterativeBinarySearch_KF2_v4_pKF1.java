package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public final class IterativeBinarySearch implements SearchAlgorithm {

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