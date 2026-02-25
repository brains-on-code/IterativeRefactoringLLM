package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return wiggleSort(array);
    }

    private int getMappedIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    private <T extends Comparable<T>> void threeWayPartitionAroundPivot(T[] array, T pivot) {
        int length = array.length;
        int left = 0;
        int current = 0;
        int right = length - 1;

        while (current <= right) {
            int mappedCurrent = getMappedIndex(current, length);
            if (array[mappedCurrent].compareTo(pivot) > 0) {
                SortUtils.swap(array, mappedCurrent, getMappedIndex(left, length));
                left++;
                current++;
            } else if (array[mappedCurrent].compareTo(pivot) < 0) {
                SortUtils.swap(array, mappedCurrent, getMappedIndex(right, length));
                right--;
            } else {
                current++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        T median = select(Arrays.asList(array), (int) floor(array.length / 2.0));

        int medianCount = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianCount++;
            }
        }

        if (array.length % 2 == 1 && medianCount == ceil(array.length / 2.0)) {
            T smallest = select(Arrays.asList(array), 0);
            if (smallest.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd Arrays if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest values in the array."
                );
            }
        }

        if (medianCount > ceil(array.length / 2.0)) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        threeWayPartitionAroundPivot(array, median);
        return array;
    }
}