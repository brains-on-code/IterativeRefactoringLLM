package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * Bitonic sort implementation.
 */
public class Class1 implements SortAlgorithm {

    private enum Direction {
        DESCENDING,
        ASCENDING,
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        final int extendedLength = nextPowerOfTwo(array.length);
        T[] extendedArray = Arrays.copyOf(array, extendedLength);

        final T maxElement = max(array);
        Arrays.fill(extendedArray, array.length, extendedLength, maxElement);

        bitonicSort(extendedArray, 0, extendedLength, Direction.ASCENDING);
        return Arrays.copyOf(extendedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
            final T[] array,
            final int start,
            final int length,
            final Direction direction
    ) {
        if (length > 1) {
            final int mid = length / 2;

            bitonicSort(array, start, mid, Direction.ASCENDING);
            bitonicSort(array, start + mid, length - mid, Direction.DESCENDING);

            bitonicMerge(array, start, length, direction);
        }
    }

    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int start,
            int length,
            Direction direction
    ) {
        if (length > 1) {
            final int mid = length / 2;

            final BiPredicate<T, T> comparator =
                    (direction == Direction.ASCENDING)
                            ? (a, b) -> a.compareTo(b) < 0
                            : (a, b) -> a.compareTo(b) > 0;

            for (int i = start; i < start + mid; i++) {
                if (!comparator.test(array[i], array[i + mid])) {
                    SortUtils.swap(array, i, i + mid);
                }
            }

            bitonicMerge(array, start, mid, direction);
            bitonicMerge(array, start + mid, length - mid, direction);
        }
    }

    private static int nextPowerOfTwo(int n) {
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

    private static <T extends Comparable<T>> T max(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}