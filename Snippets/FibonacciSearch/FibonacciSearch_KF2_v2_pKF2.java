package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int n = array.length;

        int fibPrevPrev = 0; // F(m-2)
        int fibPrev = 1;     // F(m-1)
        int fibCurrent = fibPrev + fibPrevPrev; // F(m)

        while (fibCurrent < n) {
            fibPrevPrev = fibPrev;
            fibPrev = fibCurrent;
            fibCurrent = fibPrev + fibPrevPrev;
        }

        int offset = -1;

        while (fibCurrent > 1) {
            int index = Math.min(offset + fibPrevPrev, n - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                fibCurrent = fibPrev;
                fibPrev = fibPrevPrev;
                fibPrevPrev = fibCurrent - fibPrev;
                offset = index;
            } else if (comparison > 0) {
                fibCurrent = fibPrevPrev;
                fibPrev = fibPrev - fibPrevPrev;
                fibPrevPrev = fibCurrent - fibPrev;
            } else {
                return index;
            }
        }

        if (fibPrev == 1 && offset + 1 < n && array[offset + 1].compareTo(key) == 0) {
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