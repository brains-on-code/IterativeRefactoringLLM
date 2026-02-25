package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Ternary search implementation.
 */
public class TernarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T target) {
        if (array == null || array.length == 0 || target == null) {
            return -1;
        }
        if (array.length == 1) {
            return array[0].compareTo(target) == 0 ? 0 : -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (right > left) {
            int leftComparison = array[left].compareTo(target);
            int rightComparison = array[right].compareTo(target);

            if (leftComparison == 0) {
                return left;
            }
            if (rightComparison == 0) {
                return right;
            }

            int firstThird = left + (right - left) / 3 + 1;
            int secondThird = right - (right - left) / 3 - 1;

            if (array[firstThird].compareTo(target) <= 0) {
                left = firstThird;
            } else {
                right = secondThird;
            }
        }

        return -1;
    }
}