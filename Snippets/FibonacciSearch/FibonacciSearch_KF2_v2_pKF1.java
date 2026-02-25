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

        int fibMMinusTwo = 0; // (m-2)'th Fibonacci number
        int fibMMinusOne = 1; // (m-1)'th Fibonacci number
        int fibM = fibMMinusOne + fibMMinusTwo; // m'th Fibonacci number
        int arrayLength = array.length;

        while (fibM < arrayLength) {
            fibMMinusTwo = fibMMinusOne;
            fibMMinusOne = fibM;
            fibM = fibMMinusOne + fibMMinusTwo;
        }

        int offset = -1;

        while (fibM > 1) {
            int index = Math.min(offset + fibMMinusTwo, arrayLength - 1);
            int comparisonResult = array[index].compareTo(key);

            if (comparisonResult < 0) {
                fibM = fibMMinusOne;
                fibMMinusOne = fibMMinusTwo;
                fibMMinusTwo = fibM - fibMMinusOne;
                offset = index;
            } else if (comparisonResult > 0) {
                fibM = fibMMinusTwo;
                fibMMinusOne = fibMMinusOne - fibMMinusTwo;
                fibMMinusTwo = fibM - fibMMinusOne;
            } else {
                return index;
            }
        }

        if (fibMMinusOne == 1 && offset + 1 < arrayLength && array[offset + 1].compareTo(key) == 0) {
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