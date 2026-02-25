package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * Bitonic sort implementation.
 */
public class BitonicSort implements SortAlgorithm {
    private enum Direction {
        DESCENDING,
        ASCENDING,
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        final int paddedLength = nextPowerOfTwo(array.length);
        T[] paddedArray = Arrays.copyOf(array, paddedLength);

        final T maxElement = findMax(array);
        Arrays.fill(paddedArray, array.length, paddedLength, maxElement);

        bitonicSort(paddedArray, 0, paddedLength, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
            final T[] array,
            final int startIndex,
            final int length,
            final Direction direction
    ) {
        if (length > 1) {
            final int mid = length / 2;

            bitonicSort(array, startIndex, mid, Direction.ASCENDING);
            bitonicSort(array, startIndex + mid, length - mid, Direction.DESCENDING);

            bitonicMerge(array, startIndex, length, direction);
        }
    }

    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int startIndex,
            int length,
            Direction direction
    ) {
        if (length > 1) {
            final int mid = length / 2;

            final BiPredicate<T, T> comparator =
                (direction == Direction.ASCENDING)
                    ? (a, b) -> a.compareTo(b) < 0
                    : (a, b) -> a.compareTo(b) > 0;

            for (int i = startIndex; i < startIndex + mid; i++) {
                if (!comparator.test(array[i], array[i + mid])) {
                    SortUtils.swap(array, i, i + mid);
                }
            }

            bitonicMerge(array, startIndex, mid, direction);
            bitonicMerge(array, startIndex + mid, length - mid, direction);
        }
    }

    private static int nextPowerOfTwo(int value) {
        int exponent = 0;

        if ((value & (value - 1)) == 0) {
            return value;
        }

        while (value != 0) {
            value >>= 1;
            exponent += 1;
        }

        return 1 << exponent;
    }

    private static <T extends Comparable<T>> T findMax(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}