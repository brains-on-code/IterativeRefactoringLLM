package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return wiggleSort(unsorted);
    }

    private int mapIndex(int index, int length) {
        // Virtual indexing: maps indices to odd positions first, then even
        return (2 * index + 1) % (length | 1);
    }

    private <T extends Comparable<T>> void triColorSort(T[] array, T median) {
        int length = array.length;
        int left = 0;
        int current = 0;
        int right = length - 1;

        while (current <= right) {
            int mappedCurrent = mapIndex(current, length);
            int comparison = array[mappedCurrent].compareTo(median);

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

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int length = array.length;
        if (length <= 1) {
            return array;
        }

        T median = select(Arrays.asList(array), (int) floor(length / 2.0));
        int medianCount = countOccurrences(array, median);
        double halfLengthCeil = ceil(length / 2.0);

        validateMedianCount(array, median, medianCount, halfLengthCeil);

        triColorSort(array, median);
        return array;
    }

    private <T extends Comparable<T>> int countOccurrences(T[] array, T value) {
        int count = 0;
        for (T element : array) {
            if (element.compareTo(value) == 0) {
                count++;
            }
        }
        return count;
    }

    private <T extends Comparable<T>> void validateMedianCount(
        T[] array,
        T median,
        int medianCount,
        double halfLengthCeil
    ) {
        int length = array.length;

        if (length % 2 == 1 && medianCount == halfLengthCeil) {
            T smallestValue = select(Arrays.asList(array), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median must be the smallest value in the array."
                );
            }
        }

        if (medianCount > halfLengthCeil) {
            throw new IllegalArgumentException("No more than half of the values may be the same.");
        }
    }
}