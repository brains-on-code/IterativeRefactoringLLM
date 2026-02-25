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

        int previousFib = 1;
        int currentFib = 0;
        int fibNumber = previousFib + currentFib;
        int arrayLength = array.length;

        while (fibNumber < arrayLength) {
            currentFib = previousFib;
            previousFib = fibNumber;
            fibNumber = currentFib + previousFib;
        }

        int offset = -1;

        while (fibNumber > 1) {
            int index = Math.min(offset + currentFib, arrayLength - 1);

            int comparisonResult = array[index].compareTo(key);

            if (comparisonResult < 0) {
                fibNumber = previousFib;
                previousFib = currentFib;
                currentFib = fibNumber - previousFib;
                offset = index;
            } else if (comparisonResult > 0) {
                fibNumber = currentFib;
                previousFib = previousFib - currentFib;
                currentFib = fibNumber - previousFib;
            } else {
                return index;
            }
        }

        if (previousFib == 1 && offset + 1 < arrayLength && array[offset + 1].compareTo(key) == 0) {
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