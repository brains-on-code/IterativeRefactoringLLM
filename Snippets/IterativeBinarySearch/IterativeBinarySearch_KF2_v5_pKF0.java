package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public final class IterativeBinarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (isInvalidInput(array, key)) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int middle = left + ((right - left) >>> 1);
            T middleValue = array[middle];
            int comparison = key.compareTo(middleValue);

            if (comparison == 0) {
                return middle;
            }

            if (comparison < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return -1;
    }

    private <T> boolean isInvalidInput(T[] array, T key) {
        return array == null || key == null || array.length == 0;
    }
}