package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * BitonicSort class implements the SortAlgorithm interface using the bitonic sort technique.
 */
public class BitonicSort implements SortAlgorithm {
    private enum SortDirection {
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

        final int paddedLength = nextPowerOfTwo(array.length);
        T[] paddedArray = Arrays.copyOf(array, paddedLength);

        final T maxElement = findMax(array);
        Arrays.fill(paddedArray, array.length, paddedLength, maxElement);

        bitonicSort(paddedArray, 0, paddedLength, SortDirection.ASCENDING);
        return Arrays.copyOf(paddedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
            final T[] array,
            final int startIndex,
            final int length,
            final SortDirection sortDirection
    ) {
        if (length <= 1) {
            return;
        }

        final int halfLength = length / 2;

        bitonicSort(array, startIndex, halfLength, SortDirection.ASCENDING);
        bitonicSort(array, startIndex + halfLength, length - halfLength, SortDirection.DESCENDING);

        bitonicMerge(array, startIndex, length, sortDirection);
    }

    /**
     * Merges the bitonic sequence in the specified direction.
     *
     * @param <T> the type of elements in the array, which must be Comparable
     * @param array the array containing the bitonic sequence to be merged
     * @param startIndex the starting index of the sequence to be merged
     * @param length the number of elements in the sequence to be merged
     * @param sortDirection the direction of sorting
     */
    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int startIndex,
            int length,
            SortDirection sortDirection
    ) {
        if (length <= 1) {
            return;
        }

        final int halfLength = length / 2;

        final BiPredicate<T, T> isInCorrectOrder =
                (sortDirection == SortDirection.ASCENDING)
                        ? (leftElement, rightElement) -> leftElement.compareTo(rightElement) <= 0
                        : (leftElement, rightElement) -> leftElement.compareTo(rightElement) >= 0;

        final int mergeBoundaryIndex = startIndex + halfLength;
        for (int leftIndex = startIndex; leftIndex < mergeBoundaryIndex; leftIndex++) {
            int rightIndex = leftIndex + halfLength;
            if (!isInCorrectOrder.test(array[leftIndex], array[rightIndex])) {
                SortUtils.swap(array, leftIndex, rightIndex);
            }
        }

        bitonicMerge(array, startIndex, halfLength, sortDirection);
        bitonicMerge(array, startIndex + halfLength, length - halfLength, sortDirection);
    }

    /**
     * Finds the next power of two greater than or equal to the given number.
     *
     * @param number the number
     * @return the next power of two
     */
    private static int nextPowerOfTwo(int number) {
        if (isPowerOfTwo(number)) {
            return number;
        }

        int bitCount = 0;
        int value = number;

        while (value != 0) {
            value >>= 1;
            bitCount++;
        }

        return 1 << bitCount;
    }

    private static boolean isPowerOfTwo(int number) {
        return (number & (number - 1)) == 0;
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