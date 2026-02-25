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

        int fibPrevPrev = 0; // (m-2)'th Fibonacci number
        int fibPrev = 1; // (m-1)'th Fibonacci number
        int fibCurrent = fibPrev + fibPrevPrev; // m'th Fibonacci number
        int arrayLength = array.length;

        while (fibCurrent < arrayLength) {
            fibPrevPrev = fibPrev;
            fibPrev = fibCurrent;
            fibCurrent = fibPrev + fibPrevPrev;
        }

        int offset = -1;

        while (fibCurrent > 1) {
            int probeIndex = Math.min(offset + fibPrevPrev, arrayLength - 1);
            int comparisonResult = array[probeIndex].compareTo(target);

            if (comparisonResult < 0) {
                fibCurrent = fibPrev;
                fibPrev = fibPrevPrev;
                fibPrevPrev = fibCurrent - fibPrev;
                offset = probeIndex;
            } else if (comparisonResult > 0) {
                fibCurrent = fibPrevPrev;
                fibPrev = fibPrev - fibPrevPrev;
                fibPrevPrev = fibCurrent - fibPrev;
            } else {
                return probeIndex;
            }
        }

        int lastIndex = offset + 1;
        if (fibPrev == 1 && lastIndex < arrayLength && array[lastIndex].compareTo(target) == 0) {
            return lastIndex;
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