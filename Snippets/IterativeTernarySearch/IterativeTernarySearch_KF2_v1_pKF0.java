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

        while (left <= right) {
            int leftThird = left + (right - left) / 3;
            int rightThird = right - (right - left) / 3;

            int leftCmp = array[leftThird].compareTo(key);
            int rightCmp = array[rightThird].compareTo(key);

            if (leftCmp == 0) {
                return leftThird;
            }
            if (rightCmp == 0) {
                return rightThird;
            }

            if (key.compareTo(array[leftThird]) < 0) {
                right = leftThird - 1;
            } else if (key.compareTo(array[rightThird]) > 0) {
                left = rightThird + 1;
            } else {
                left = leftThird + 1;
                right = rightThird - 1;
            }
        }

        return -1;
    }
}