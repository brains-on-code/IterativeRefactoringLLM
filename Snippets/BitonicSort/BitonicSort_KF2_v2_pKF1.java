package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

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
            final SortDirection direction
    ) {
        if (length <= 1) {
            return;
        }

        final int halfLength = length / 2;

        bitonicSort(array, startIndex, halfLength, SortDirection.ASCENDING);
        bitonicSort(array, startIndex + halfLength, length - halfLength, SortDirection.DESCENDING);

        bitonicMerge(array, startIndex, length, direction);
    }

    private <T extends Comparable<T>> void bitonicMerge(
            T[] array,
            int startIndex,
            int length,
            SortDirection direction
    ) {
        if (length <= 1) {
            return;
        }

        final int halfLength = length / 2;

        final BiPredicate<T, T> isInCorrectOrder =
                (direction == SortDirection.ASCENDING)
                        ? (first, second) -> first.compareTo(second) <= 0
                        : (first, second) -> first.compareTo(second) >= 0;

        for (int currentIndex = startIndex; currentIndex < startIndex + halfLength; currentIndex++) {
            int pairedIndex = currentIndex + halfLength;
            if (!isInCorrectOrder.test(array[currentIndex], array[pairedIndex])) {
                SortUtils.swap(array, currentIndex, pairedIndex);
            }
        }

        bitonicMerge(array, startIndex, halfLength, direction);
        bitonicMerge(array, startIndex + halfLength, length - halfLength, direction);
    }

    private static int nextPowerOfTwo(int value) {
        if ((value & (value - 1)) == 0) {
            return value;
        }

        int bitCount = 0;
        int tempValue = value;

        while (tempValue != 0) {
            tempValue >>= 1;
            bitCount++;
        }

        return 1 << bitCount;
    }

    private static <T extends Comparable<T>> T findMax(final T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}