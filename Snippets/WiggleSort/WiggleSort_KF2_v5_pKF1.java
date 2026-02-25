package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return wiggleSort(array);
    }

    private int toVirtualIndex(int index, int size) {
        return (2 * index + 1) % (size | 1);
    }

    private <T extends Comparable<T>> void threeWayPartitionByMedian(T[] array, T median) {
        int size = array.length;
        int left = 0;
        int current = 0;
        int right = size - 1;

        while (current <= right) {
            int virtualCurrent = toVirtualIndex(current, size);
            int virtualLeft = toVirtualIndex(left, size);
            int virtualRight = toVirtualIndex(right, size);

            int comparison = array[virtualCurrent].compareTo(median);

            if (comparison > 0) {
                SortUtils.swap(array, virtualCurrent, virtualLeft);
                left++;
                current++;
            } else if (comparison < 0) {
                SortUtils.swap(array, virtualCurrent, virtualRight);
                right--;
            } else {
                current++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int size = array.length;

        T median = select(Arrays.asList(array), (int) floor(size / 2.0));

        int medianCount = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianCount++;
            }
        }

        if (size % 2 == 1 && medianCount == ceil(size / 2.0)) {
            T minValue = select(Arrays.asList(array), 0);
            if (minValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest value in the array."
                );
            }
        }

        if (medianCount > ceil(size / 2.0)) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        threeWayPartitionByMedian(array, median);
        return array;
    }
}