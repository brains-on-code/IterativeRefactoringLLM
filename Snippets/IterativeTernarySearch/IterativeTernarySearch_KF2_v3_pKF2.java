package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class IterativeTernarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (array == null || key == null || array.length == 0) {
            return -1;
        }

        if (array.length == 1) {
            return array[0].compareTo(key) == 0 ? 0 : -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (right > left) {
            int leftComparison = array[left].compareTo(key);
            int rightComparison = array[right].compareTo(key);

            if (leftComparison == 0) {
                return left;
            }
            if (rightComparison == 0) {
                return right;
            }

            int leftThird = left + (right - left) / 3 + 1;
            int rightThird = right - (right - left) / 3 - 1;

            if (array[leftThird].compareTo(key) <= 0) {
                left = leftThird;
            } else {
                right = rightThird;
            }
        }

        return -1;
    }
}