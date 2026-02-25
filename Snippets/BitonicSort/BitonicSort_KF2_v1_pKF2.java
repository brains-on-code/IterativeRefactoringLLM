package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class BitonicSort implements SortAlgorithm {

    private enum Direction {
        ASCENDING,
        DESCENDING
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
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int half = length / 2;

        bitonicSort(array, low, half, Direction.ASCENDING);
        bitonicSort(array, low + half, length - half, Direction.DESCENDING);

        bitonicMerge(array, low, length, direction);
    }

    private <T extends Comparable<T>> void bitonicMerge(
        T[] array,
        int low,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int half = length / 2;

        BiPredicate<T, T> inCorrectOrder =
            (direction == Direction.ASCENDING)
                ? (a, b) -> a.compareTo(b) <= 0
                : (a, b) -> a.compareTo(b) >= 0;

        for (int i = low; i < low + half; i++) {
            if (!inCorrectOrder.test(array[i], array[i + half])) {
                SortUtils.swap(array, i, i + half);
            }
        }

        bitonicMerge(array, low, half, direction);
        bitonicMerge(array, low + half, length - half, direction);
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
        return Arrays.stream(array)
            .max(Comparable::compareTo)
            .orElseThrow();
    }
}