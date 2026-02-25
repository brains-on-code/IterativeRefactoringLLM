package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * Bitonic sort implementation.
 */
public class Class1 implements SortAlgorithm {

    private enum Direction {
        ASCENDING,
        DESCENDING
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        int extendedLength = nextPowerOfTwo(array.length);
        T[] extendedArray = Arrays.copyOf(array, extendedLength);

        T maxElement = max(array);
        Arrays.fill(extendedArray, array.length, extendedLength, maxElement);

        bitonicSort(extendedArray, 0, extendedLength, Direction.ASCENDING);
        return Arrays.copyOf(extendedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
            T[] array,
            int start,
            int length,
            Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int mid = length / 2;

        bitonicSort(array, start, mid, Direction.ASCENDING);
        bitonicSort(array, start + mid, length - mid, Direction.DESCENDING);

        bitonicMerge(array, start, length, direction);
    }

    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int start,
            int length,
            Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int mid = length / 2;
        BiPredicate<T, T> comparator = createComparator(direction);

        for (int i = start; i < start + mid; i++) {
            if (!comparator.test(array[i], array[i + mid])) {
                SortUtils.swap(array, i, i + mid);
            }
        }

        bitonicMerge(array, start, mid, direction);
        bitonicMerge(array, start + mid, length - mid, direction);
    }

    private static <T extends Comparable<T>> BiPredicate<T, T> createComparator(Direction direction) {
        return (direction == Direction.ASCENDING)
                ? (a, b) -> a.compareTo(b) <= 0
                : (a, b) -> a.compareTo(b) >= 0;
    }

    private static int nextPowerOfTwo(int n) {
        if (n <= 1) {
            return 1;
        }

        if ((n & (n - 1)) == 0) {
            return n;
        }

        int exponent = 32 - Integer.numberOfLeadingZeros(n - 1);
        return 1 << exponent;
    }

    private static <T extends Comparable<T>> T max(T[] array) {
        return Arrays.stream(array)
                .max(Comparable::compareTo)
                .orElseThrow();
    }
}