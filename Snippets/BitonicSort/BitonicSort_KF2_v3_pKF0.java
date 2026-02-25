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
        int count,
        Direction direction
    ) {
        if (count <= 1) {
            return;
        }

        int mid = count / 2;

        bitonicSort(array, low, mid, Direction.ASCENDING);
        bitonicSort(array, low + mid, mid, Direction.DESCENDING);

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

        int mid = count / 2;
        BiPredicate<T, T> inCorrectOrder = getOrderPredicate(direction);

        for (int i = low; i < low + mid; i++) {
            if (!inCorrectOrder.test(array[i], array[i + mid])) {
                SortUtils.swap(array, i, i + mid);
            }
        }

        bitonicMerge(array, low, mid, direction);
        bitonicMerge(array, low + mid, mid, direction);
    }

    private static <T extends Comparable<T>> BiPredicate<T, T> getOrderPredicate(Direction direction) {
        return direction == Direction.ASCENDING
            ? (a, b) -> a.compareTo(b) <= 0
            : (a, b) -> a.compareTo(b) >= 0;
    }

    private static int nextPowerOfTwo(int n) {
        if (n <= 1) {
            return 1;
        }

        if (isPowerOfTwo(n)) {
            return n;
        }

        int power = 1;
        while (power < n) {
            power <<= 1;
        }
        return power;
    }

    private static boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    private static <T extends Comparable<T>> T max(T[] array) {
        return Arrays.stream(array)
            .max(Comparable::compareTo)
            .orElseThrow();
    }
}