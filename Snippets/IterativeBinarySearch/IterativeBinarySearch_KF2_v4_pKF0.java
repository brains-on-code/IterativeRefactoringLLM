package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public final class IterativeBinarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (isInvalidInput(array, key)) {
            return -1;
        }

        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + ((rightIndex - leftIndex) >>> 1);
            T middleValue = array[middleIndex];
            int comparisonResult = key.compareTo(middleValue);

            if (comparisonResult == 0) {
                return middleIndex;
            }

            if (comparisonResult < 0) {
                rightIndex = middleIndex - 1;
            } else {
                leftIndex = middleIndex + 1;
            }
        }

        return -1;
    }

    private <T> boolean isInvalidInput(T[] array, T key) {
        return array == null || key == null || array.length == 0;
    }
}