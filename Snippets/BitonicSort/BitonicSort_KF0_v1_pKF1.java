package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * BitonicSort class implements the SortAlgorithm interface using the bitonic sort technique.
 */
public class BitonicSort implements SortAlgorithm {
    private enum Direction {
        DESCENDING,
        ASCENDING,
    }

    /**
     * Sorts the given array using the Bitonic Sort algorithm.
     *
     * @param <T> the type of elements in the array, which must implement the Comparable interface
     * @param array the array to be sorted
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        final int paddedSize = nextPowerOfTwo(array.length);
        T[] paddedArray = Arrays.copyOf(array, paddedSize);

        final T maxValue = findMax(array);
        Arrays.fill(paddedArray, array.length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
            final T[] array,
            final int startIndex,
            final int length,
            final Direction direction
    ) {
        if (length > 1) {
            final int halfLength = length / 2;

            bitonicSort(array, startIndex, halfLength, Direction.ASCENDING);
            bitonicSort(array, startIndex + halfLength, length - halfLength, Direction.DESCENDING);

            bitonicMerge(array, startIndex, length, direction);
        }
    }

    /**
     * Merges the bitonic sequence in the specified direction.
     *
     * @param <T> the type of elements in the array, which must be Comparable
     * @param array the array containing the bitonic sequence to be merged
     * @param startIndex the starting index of the sequence to be merged
     * @param length the number of elements in the sequence to be merged
     * @param direction the direction of sorting
     */
    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int startIndex,
            int length,
            Direction direction
    ) {
        if (length > 1) {
            final int halfLength = length / 2;

            final BiPredicate<T, T> isInCorrectOrder =
                    (direction == Direction.ASCENDING)
                            ? (first, second) -> first.compareTo(second) <= 0
                            : (first, second) -> first.compareTo(second) >= 0;

            for (int index = startIndex; index < startIndex + halfLength; index++) {
                if (!isInCorrectOrder.test(array[index], array[index + halfLength])) {
                    SortUtils.swap(array, index, index + halfLength);
                }
            }

            bitonicMerge(array, startIndex, halfLength, direction);
            bitonicMerge(array, startIndex + halfLength, length - halfLength, direction);
        }
    }

    /**
     * Finds the next power of two greater than or equal to the given number.
     *
     * @param number the number
     * @return the next power of two
     */
    private static int nextPowerOfTwo(int number) {
        int bitCount = 0;

        if ((number & (number - 1)) == 0) {
            return number;
        }

        while (number != 0) {
            number >>= 1;
            bitCount += 1;
        }

        return 1 << bitCount;
    }

    /**
     * Finds the maximum element in the given array.
     *
     * @param <T> the type of elements in the array, which must implement the Comparable interface
     * @param array the array to be searched
     * @return the maximum element in the array
     */
    private static <T extends Comparable<T>> T findMax(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}