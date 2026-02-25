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

            int leftThirdComparison = array[leftThird].compareTo(key);
            int rightThirdComparison = array[rightThird].compareTo(key);

            if (leftThirdComparison == 0) {
                return leftThird;
            }
            if (rightThirdComparison == 0) {
                return rightThird;
            }

            if (leftThirdComparison > 0) {
                right = leftThird - 1;
            } else if (rightThirdComparison < 0) {
                left = rightThird + 1;
            } else {
                left = leftThird + 1;
                right = rightThird - 1;
            }
        }

        return -1;
    }
}