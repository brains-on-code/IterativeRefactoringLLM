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

        T maxElement = findMax(array);
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

        int end = start + mid;
        for (int i = start; i < end; i++) {
            int pairedIndex = i + mid;
            if (!comparator.test(array[i], array[pairedIndex])) {
                SortUtils.swap(array, i, pairedIndex);
            }
        }

        bitonicMerge(array, start, mid, direction);
        bitonicMerge(array, start + mid, length - mid, direction);
    }

    private static <T extends Comparable<T>> BiPredicate<T, T> createComparator(Direction direction) {
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

        int exponent = 32 - Integer.numberOfLeadingZeros(n - 1);
        return 1 << exponent;
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