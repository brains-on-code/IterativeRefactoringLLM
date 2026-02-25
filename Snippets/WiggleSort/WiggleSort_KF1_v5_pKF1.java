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

    private int toVirtualIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    private <T extends Comparable<T>> void threeWayPartitionByPivot(T[] array, T pivot) {
        int length = array.length;
        int left = 0;
        int current = 0;
        int right = length - 1;

        while (current <= right) {
            int virtualCurrent = toVirtualIndex(current, length);
            int comparison = array[virtualCurrent].compareTo(pivot);

            if (comparison > 0) {
                SortUtils.swap(array, virtualCurrent, toVirtualIndex(left, length));
                left++;
                current++;
            } else if (comparison < 0) {
                SortUtils.swap(array, virtualCurrent, toVirtualIndex(right, length));
                right--;
            } else {
                current++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int length = array.length;
        T median = select(Arrays.asList(array), (int) floor(length / 2.0));

        int medianCount = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianCount++;
            }
        }

        if (length % 2 == 1 && medianCount == ceil(length / 2.0)) {
            T minimum = select(Arrays.asList(array), 0);
            if (minimum.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest value in the array."
                );
            }
        }

        if (medianCount > ceil(length / 2.0)) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        threeWayPartitionByPivot(array, median);
        return array;
    }
}