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

        T maxValue = findMax(array);
        Arrays.fill(paddedArray, array.length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
        T[] array,
        int startIndex,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int halfLength = length / 2;

        bitonicSort(array, startIndex, halfLength, Direction.ASCENDING);
        bitonicSort(array, startIndex + halfLength, halfLength, Direction.DESCENDING);

        bitonicMerge(array, startIndex, length, direction);
    }

    private <T extends Comparable<T>> void bitonicMerge(
        T[] array,
        int startIndex,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int halfLength = length / 2;
        BiPredicate<T, T> inCorrectOrder = getOrderPredicate(direction);

        int midIndex = startIndex + halfLength;
        for (int left = startIndex, right = midIndex; left < midIndex; left++, right++) {
            if (!inCorrectOrder.test(array[left], array[right])) {
                SortUtils.swap(array, left, right);
            }
        }

        bitonicMerge(array, startIndex, halfLength, direction);
        bitonicMerge(array, startIndex + halfLength, halfLength, direction);
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

    private static <T extends Comparable<T>> T findMax(T[] array) {
        return Arrays.stream(array)
            .max(Comparable::compareTo)
            .orElseThrow();
    }
}