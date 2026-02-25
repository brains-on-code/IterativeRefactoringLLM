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

    /**
     * Maps a logical index to its "virtual" index used for wiggle sorting.
     * Produces the sequence: 1, 3, 5, ..., 0, 2, 4, ...
     */
    private int mapIndex(int index, int n) {
        return (2 * index + 1) % (n | 1);
    }

    /**
     * Three-way partition (Dutch National Flag) around the median using the
     * virtual index mapping:
     * - elements greater than the median go to the front (odd virtual indices),
     * - elements less than the median go to the back (even virtual indices),
     * - elements equal to the median stay in the middle.
     */
    private <T extends Comparable<T>> void triColorSort(T[] array, T median) {
        int n = array.length;
        int left = 0;
        int current = 0;
        int right = n - 1;

        while (current <= right) {
            int mappedCurrent = mapIndex(current, n);
            int cmp = array[mappedCurrent].compareTo(median);

            if (cmp > 0) {
                SortUtils.swap(array, mappedCurrent, mapIndex(left, n));
                left++;
                current++;
            } else if (cmp < 0) {
                SortUtils.swap(array, mappedCurrent, mapIndex(right, n));
                right--;
            } else {
                current++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int n = array.length;

        // Find the median element via QuickSelect.
        T median = select(Arrays.asList(array), (int) floor(n / 2.0));

        // Count occurrences of the median.
        int medianCount = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianCount++;
            }
        }

        // For odd-length arrays:
        // If the median appears ceil(n / 2) times, a valid wiggle ordering
        // is only possible if that median is the smallest value in the array.
        if (n % 2 == 1 && medianCount == ceil(n / 2.0)) {
            T smallestValue = select(Arrays.asList(array), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median must be the smallest value in the array."
                );
            }
        }

        // General wiggle sort constraint:
        // No value may appear more than ceil(n / 2) times.
        if (medianCount > ceil(n / 2.0)) {
            throw new IllegalArgumentException(
                "No more than half of the elements may be the same value."
            );
        }

        triColorSort(array, median);
        return array;
    }
}