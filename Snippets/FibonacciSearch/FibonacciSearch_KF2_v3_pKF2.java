package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        int fibM2 = 0;
        int fibM1 = 1;
        int fibM = fibM1 + fibM2;

        while (fibM < n) {
            fibM2 = fibM1;
            fibM1 = fibM;
            fibM = fibM1 + fibM2;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = Math.min(offset + fibM2, n - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                fibM = fibM1;
                fibM1 = fibM2;
                fibM2 = fibM - fibM1;
                offset = index;
            } else if (comparison > 0) {
                fibM = fibM2;
                fibM1 = fibM1 - fibM2;
                fibM2 = fibM - fibM1;
            } else {
                return index;
            }
        }

        if (fibM1 == 1 && offset + 1 < n && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private <T extends Comparable<T>> void validateInput(T[] array, T key) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array must not be null or empty.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
    }

    private <T extends Comparable<T>> boolean isSorted(T[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1].compareTo(array[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}