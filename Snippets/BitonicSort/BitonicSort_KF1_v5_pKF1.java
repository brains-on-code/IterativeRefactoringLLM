package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * Bitonic sort implementation.
 */
public class BitonicSort implements SortAlgorithm {
    private enum SortDirection {
        DESCENDING,
        ASCENDING,
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] inputArray) {
        if (inputArray.length == 0) {
            return inputArray;
        }

        final int paddedLength = nextPowerOfTwo(inputArray.length);
        T[] paddedArray = Arrays.copyOf(inputArray, paddedLength);

        final T maxElement = findMax(inputArray);
        Arrays.fill(paddedArray, inputArray.length, paddedLength, maxElement);

        bitonicSort(paddedArray, 0, paddedLength, SortDirection.ASCENDING);
        return Arrays.copyOf(paddedArray, inputArray.length);
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
        bitonicSort(array, startIndex + halfLength, halfLength, SortDirection.DESCENDING);

        bitonicMerge(array, startIndex, length, sortDirection);
    }

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

        for (int leftIndex = startIndex; leftIndex < startIndex + halfLength; leftIndex++) {
            int rightIndex = leftIndex + halfLength;
            if (!isInCorrectOrder.test(array[leftIndex], array[rightIndex])) {
                SortUtils.swap(array, leftIndex, rightIndex);
            }
        }

        bitonicMerge(array, startIndex, halfLength, sortDirection);
        bitonicMerge(array, startIndex + halfLength, halfLength, sortDirection);
    }

    private static int nextPowerOfTwo(int value) {
        if (value <= 0) {
            return 1;
        }

        if ((value & (value - 1)) == 0) {
            return value;
        }

        int bitCount = 0;
        int currentValue = value;

        while (currentValue != 0) {
            currentValue >>= 1;
            bitCount++;
        }

        return 1 << bitCount;
    }

    private static <T extends Comparable<T>> T findMax(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}