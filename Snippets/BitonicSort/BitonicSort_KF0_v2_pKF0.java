package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * BitonicSort class implements the SortAlgorithm interface using the bitonic sort technique.
 */
public class BitonicSort implements SortAlgorithm {

    private enum Direction {
        ASCENDING,
        DESCENDING
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        int length = array.length;
        if (length == 0) {
            return array;
        }

        int paddedSize = nextPowerOfTwo(length);
        T[] paddedArray = Arrays.copyOf(array, paddedSize);

        T maxValue = max(array);
        Arrays.fill(paddedArray, length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, length);
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
        bitonicSort(array, low + half, half, Direction.DESCENDING);

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
        BiPredicate<T, T> inCorrectOrder = getOrderPredicate(direction);

        int mid = low + half;
        for (int i = low; i < mid; i++) {
            int j = i + half;
            if (!inCorrectOrder.test(array[i], array[j])) {
                SortUtils.swap(array, i, j);
            }
        }

        bitonicMerge(array, low, half, direction);
        bitonicMerge(array, low + half, half, direction);
    }

    private static <T extends Comparable<T>> BiPredicate<T, T> getOrderPredicate(Direction direction) {
        return direction == Direction.ASCENDING
            ? (a, b) -> a.compareTo(b) <= 0
            : (a, b) -> a.compareTo(b) >= 0;
    }

    private static int nextPowerOfTwo(int n) {
        if (n <= 0) {
            return 1;
        }

        if ((n & (n - 1)) == 0) {
            return n;
        }

        int power = 1;
        while (power < n) {
            power <<= 1;
        }

        return power;
    }

    private static <T extends Comparable<T>> T max(T[] array) {
        return Arrays.stream(array)
            .max(Comparable::compareTo)
            .orElseThrow();
    }
}