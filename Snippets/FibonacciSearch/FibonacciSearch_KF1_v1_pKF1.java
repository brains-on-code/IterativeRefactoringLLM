package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }

        int fibMMinus2 = 0; // (m-2)'th Fibonacci number
        int fibMMinus1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibMMinus1 + fibMMinus2; // m'th Fibonacci number
        int arrayLength = array.length;

        while (fibM < arrayLength) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus2 + fibMMinus1;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = Math.min(offset + fibMMinus2, arrayLength - 1);

            int comparison = array[index].compareTo(key);
            if (comparison < 0) {
                fibM = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
                offset = index;
            } else if (comparison > 0) {
                fibM = fibMMinus2;
                fibMMinus1 = fibMMinus1 - fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
            } else {
                return index;
            }
        }

        if (fibMMinus1 == 1 && offset + 1 < arrayLength && array[offset + 1].compareTo(key) == 0) {
            return offset + 1;
        }

        return -1;
    }

    private boolean isSorted(Comparable[] array) {
        for (int index = 1; index < array.length; index++) {
            if (array[index - 1].compareTo(array[index]) > 0) {
                return false;
            }
        }
        return true;
    }
}