package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * Bitonic sort implementation.
 */
public class BitonicSort implements SortAlgorithm {

    private enum Direction {
        DESCENDING,
        ASCENDING
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        int paddedSize = nextPowerOfTwo(array.length);
        T[] paddedArray = Arrays.copyOf(array, paddedSize);

        T maxValue = max(array);
        Arrays.fill(paddedArray, array.length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
        T[] array,
        int low,
        int count,
        Direction direction
    ) {
        if (count <= 1) {
            return;
        }

        int half = count / 2;

        bitonicSort(array, low, half, Direction.ASCENDING);
        bitonicSort(array, low + half, count - half, Direction.DESCENDING);

        bitonicMerge(array, low, count, direction);
    }

    private <T extends Comparable<T>> void bitonicMerge(
        T[] array,
        int low,
        int count,
        Direction direction
    ) {
        if (count <= 1) {
            return;
        }

        int half = count / 2;
        BiPredicate<T, T> inOrder =
            (direction == Direction.ASCENDING)
                ? (a, b) -> a.compareTo(b) <= 0
                : (a, b) -> a.compareTo(b) >= 0;

        for (int i = low; i < low + half; i++) {
            if (!inOrder.test(array[i], array[i + half])) {
                SortUtils.swap(array, i, i + half);
            }
        }

        bitonicMerge(array, low, half, direction);
        bitonicMerge(array, low + half, count - half, direction);
    }

    private static int nextPowerOfTwo(int n) {
        if (n <= 0) {
            return 1;
        }

        if ((n & (n - 1)) == 0) {
            return n;
        }

        int exponent = 0;
        while (n != 0) {
            n >>= 1;
            exponent++;
        }

        return 1 << exponent;
    }

    private static <T extends Comparable<T>> T max(T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}