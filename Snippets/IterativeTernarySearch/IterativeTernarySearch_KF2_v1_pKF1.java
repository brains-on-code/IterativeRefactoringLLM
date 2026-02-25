package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class IterativeTernarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T target) {
        if (array == null || array.length == 0 || target == null) {
            return -1;
        }
        if (array.length == 1) {
            return array[0].compareTo(target) == 0 ? 0 : -1;
        }

        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (rightIndex > leftIndex) {
            int leftComparison = array[leftIndex].compareTo(target);
            int rightComparison = array[rightIndex].compareTo(target);

            if (leftComparison == 0) {
                return leftIndex;
            }
            if (rightComparison == 0) {
                return rightIndex;
            }

            int leftThirdIndex = leftIndex + (rightIndex - leftIndex) / 3 + 1;
            int rightThirdIndex = rightIndex - (rightIndex - leftIndex) / 3 - 1;

            if (array[leftThirdIndex].compareTo(target) <= 0) {
                leftIndex = leftThirdIndex;
            } else {
                rightIndex = rightThirdIndex;
            }
        }

        return -1;
    }
}