package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

public class FibonacciSearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T target) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Input array must not be empty.");
        }
        if (!isSorted(array)) {
            throw new IllegalArgumentException("Input array must be sorted.");
        }
        if (target == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }

        int fibMMinus2 = 0; // (m-2)'th Fibonacci number
        int fibMMinus1 = 1; // (m-1)'th Fibonacci number
        int fibM = fibMMinus1 + fibMMinus2; // m'th Fibonacci number
        int arrayLength = array.length;

        while (fibM < arrayLength) {
            fibMMinus2 = fibMMinus1;
            fibMMinus1 = fibM;
            fibM = fibMMinus1 + fibMMinus2;
        }

        int offset = -1;

        while (fibM > 1) {
            int probeIndex = Math.min(offset + fibMMinus2, arrayLength - 1);
            int comparisonResult = array[probeIndex].compareTo(target);

            if (comparisonResult < 0) {
                fibM = fibMMinus1;
                fibMMinus1 = fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
                offset = probeIndex;
            } else if (comparisonResult > 0) {
                fibM = fibMMinus2;
                fibMMinus1 = fibMMinus1 - fibMMinus2;
                fibMMinus2 = fibM - fibMMinus1;
            } else {
                return probeIndex;
            }
        }

        int lastIndexToCheck = offset + 1;
        if (fibMMinus1 == 1 && lastIndexToCheck < arrayLength && array[lastIndexToCheck].compareTo(target) == 0) {
            return lastIndexToCheck;
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