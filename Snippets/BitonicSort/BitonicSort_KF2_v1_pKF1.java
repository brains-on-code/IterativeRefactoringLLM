package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class BitonicSort implements SortAlgorithm {

    private enum SortDirection {
        DESCENDING,
        ASCENDING,
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] originalArray) {
        if (originalArray.length == 0) {
            return originalArray;
        }

        final int paddedSize = nextPowerOfTwo(originalArray.length);
        T[] paddedArray = Arrays.copyOf(originalArray, paddedSize);

        final T maxValue = max(originalArray);
        Arrays.fill(paddedArray, originalArray.length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, SortDirection.ASCENDING);
        return Arrays.copyOf(paddedArray, originalArray.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
            final T[] array,
            final int startIndex,
            final int length,
            final SortDirection direction
    ) {
        if (length > 1) {
            final int halfLength = length / 2;

            bitonicSort(array, startIndex, halfLength, SortDirection.ASCENDING);
            bitonicSort(array, startIndex + halfLength, length - halfLength, SortDirection.DESCENDING);

            bitonicMerge(array, startIndex, length, direction);
        }
    }

    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int startIndex,
            int length,
            SortDirection direction
    ) {
        if (length > 1) {
            final int halfLength = length / 2;

            final BiPredicate<T, T> isInCorrectOrder =
                    (direction == SortDirection.ASCENDING)
                            ? (first, second) -> first.compareTo(second) <= 0
                            : (first, second) -> first.compareTo(second) >= 0;

            for (int i = startIndex; i < startIndex + halfLength; i++) {
                if (!isInCorrectOrder.test(array[i], array[i + halfLength])) {
                    SortUtils.swap(array, i, i + halfLength);
                }
            }

            bitonicMerge(array, startIndex, halfLength, direction);
            bitonicMerge(array, startIndex + halfLength, length - halfLength, direction);
        }
    }

    private static int nextPowerOfTwo(int value) {
        int bitCount = 0;

        if ((value & (value - 1)) == 0) {
            return value;
        }

        while (value != 0) {
            value >>= 1;
            bitCount += 1;
        }

        return 1 << bitCount;
    }

    private static <T extends Comparable<T>> T max(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}