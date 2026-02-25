package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * Bitonic sort implementation.
 *
 * <p>Bitonic sort works efficiently on sequences whose length is a power of two.
 * For arrays whose length is not a power of two, this implementation pads the
 * array with copies of the maximum element so that the length becomes the next
 * power of two, performs the sort, and then truncates back to the original
 * length.</p>
 */
public class Class1 implements SortAlgorithm {

    private enum Direction {
        DESCENDING,
        ASCENDING
    }

    /**
     * Sorts the given array using bitonic sort in ascending order.
     *
     * @param array the array to sort
     * @param <T>   the element type, which must be {@link Comparable}
     * @return a sorted copy of the input array
     */
    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        if (array.length == 0) {
            return array;
        }

        final int paddedLength = nextPowerOfTwo(array.length);
        T[] padded = Arrays.copyOf(array, paddedLength);

        final T maxElement = maxOf(array);
        Arrays.fill(padded, array.length, paddedLength, maxElement);

        bitonicSort(padded, 0, paddedLength, Direction.ASCENDING);
        return Arrays.copyOf(padded, array.length);
    }

    /**
     * Recursively builds a bitonic sequence and sorts it in the given direction.
     *
     * @param array     the array containing the sequence
     * @param start     starting index of the sequence
     * @param length    length of the sequence
     * @param direction desired sort direction
     * @param <T>       element type
     */
    private <T extends Comparable<T>> void bitonicSort(
            final T[] array,
            final int start,
            final int length,
            final Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        final int mid = length / 2;

        bitonicSort(array, start, mid, Direction.ASCENDING);
        bitonicSort(array, start + mid, length - mid, Direction.DESCENDING);

        bitonicMerge(array, start, length, direction);
    }

    /**
     * Merges a bitonic sequence into a fully sorted sequence in the given direction.
     *
     * @param array     the array containing the sequence
     * @param start     starting index of the sequence
     * @param length    length of the sequence
     * @param direction desired sort direction
     * @param <T>       element type
     */
    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int start,
            int length,
            Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        final int mid = length / 2;

        final BiPredicate<T, T> inCorrectOrder =
                (direction == Direction.ASCENDING)
                        ? (a, b) -> a.compareTo(b) <= 0
                        : (a, b) -> a.compareTo(b) >= 0;

        for (int i = start; i < start + mid; i++) {
            if (!inCorrectOrder.test(array[i], array[i + mid])) {
                SortUtils.swap(array, i, i + mid);
            }
        }

        bitonicMerge(array, start, mid, direction);
        bitonicMerge(array, start + mid, length - mid, direction);
    }

    /**
     * Returns the smallest power of two that is greater than or equal to {@code n}.
     *
     * @param n a positive integer
     * @return the next power of two greater than or equal to {@code n}
     */
    private static int nextPowerOfTwo(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        if ((n & (n - 1)) == 0) {
            return n;
        }

        int exponent = 0;
        int value = n;

        while (value != 0) {
            value >>= 1;
            exponent++;
        }

        return 1 << exponent;
    }

    /**
     * Returns the maximum element of the given non-empty array.
     *
     * @param array the array to search
     * @param <T>   element type
     * @return the maximum element
     */
    private static <T extends Comparable<T>> T maxOf(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}