package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return partitionAroundMedian(array);
    }

    private int mapIndex(int index, int length) {
        // Virtual index mapping used for wiggle-style rearrangement
        return (2 * index + 1) % (length | 1);
    }

    private <T extends Comparable<T>> void threeWayPartition(T[] array, T pivot) {
        final int length = array.length;
        int left = 0;
        int current = 0;
        int right = length - 1;

        while (current <= right) {
            int mappedCurrent = mapIndex(current, length);
            int comparison = array[mappedCurrent].compareTo(pivot);

            if (comparison > 0) {
                SortUtils.swap(array, mappedCurrent, mapIndex(left, length));
                left++;
                current++;
            } else if (comparison < 0) {
                SortUtils.swap(array, mappedCurrent, mapIndex(right, length));
                right--;
            } else {
                current++;
            }
        }
    }

    private <T extends Comparable<T>> T[] partitionAroundMedian(T[] array) {
        final int length = array.length;
        final double halfLength = length / 2.0;

        T median = select(Arrays.asList(array), (int) floor(halfLength));

        int medianCount = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianCount++;
            }
        }

        int ceilHalfLength = (int) ceil(halfLength);

        if (isOdd(length) && medianCount == ceilHalfLength) {
            validateMedianIsSmallest(array, median);
        }

        if (medianCount > ceilHalfLength) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        threeWayPartition(array, median);
        return array;
    }

    private boolean isOdd(int value) {
        return (value & 1) == 1;
    }

    private <T extends Comparable<T>> void validateMedianIsSmallest(T[] array, T median) {
        T smallest = select(Arrays.asList(array), 0);
        if (smallest.compareTo(median) != 0) {
            throw new IllegalArgumentException(
                "For odd arrays, if the median appears ceil(n/2) times, "
                    + "the median has to be the smallest value in the array."
            );
        }
    }
}