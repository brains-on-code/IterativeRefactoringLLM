package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        validateInput(array, key);

        int length = array.length;

        int fibMinusTwo = 0; // (m-2)th Fibonacci number
        int fibMinusOne = 1; // (m-1)th Fibonacci number
        int fibCurrent = fibMinusOne + fibMinusTwo; // mth Fibonacci number

        // Find the smallest Fibonacci number greater than or equal to length
        while (fibCurrent < length) {
            fibMinusTwo = fibMinusOne;
            fibMinusOne = fibCurrent;
            fibCurrent = fibMinusOne + fibMinusTwo;
        }

        int offset = -1;

        while (fibCurrent > 1) {
            int index = Math.min(offset + fibMinusTwo, length - 1);
            int comparison = array[index].compareTo(key);

            if (comparison < 0) {
                // Move three Fibonacci numbers down
                fibCurrent = fibMinusOne;
                fibMinusOne = fibMinusTwo;
                fibMinusTwo = fibCurrent - fibMinusOne;
                offset = index;
            } else if (comparison > 0) {
                // Move two Fibonacci numbers down
                fibCurrent = fibMinusTwo;
                fibMinusOne = fibMinusOne - fibMinusTwo;
                fibMinusTwo = fibCurrent - fibMinusOne;
            } else {
                return index;
            }
        }

        int lastIndex = offset + 1;
        if (fibMinusOne == 1 && lastIndex < length && array[lastIndex].compareTo(key) == 0) {
            return lastIndex;
        }

        return -1;
    }

    private <T extends Comparable<T>> void validateInput(T[] array, T key) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array must not be null or empty.");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
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