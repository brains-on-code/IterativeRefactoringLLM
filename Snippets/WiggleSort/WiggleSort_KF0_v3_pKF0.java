package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

/**
 * A wiggle sort implementation based on John L.'s answer in:
 * https://cs.stackexchange.com/questions/125372/how-to-wiggle-sort-an-array-in-linear-time-complexity
 *
 * Not all arrays are wiggle-sortable. This algorithm will detect some obviously
 * non–wiggle-sortable arrays and throw an error, but there are exceptions that
 * won't be caught, for example [1, 2, 2].
 */
public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return wiggleSort(unsorted);
    }

    /**
     * Index mapping used to rearrange elements into wiggle order.
     * Maps logical index to its "virtual" index.
     */
    private int mapIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    /**
     * Modified Dutch National Flag Sort. See also: sorts/DutchNationalFlagSort
     *
     * Partitions the array into three groups relative to the median:
     * - greater than median
     * - equal to median
     * - smaller than median
     *
     * @param array  array to sort into groups "greater", "equal" and "smaller" than median
     * @param median defines the groups
     * @param <T>    extends interface Comparable
     */
    private <T extends Comparable<T>> void triColorSort(T[] array, T median) {
        final int length = array.length;
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
        final int length = array.length;

        if (length <= 1) {
            return array;
        }

        T median = select(Arrays.asList(array), (int) floor(length / 2.0));
        int numMedians = countOccurrences(array, median);

        validateWiggleSortable(array, median, length, numMedians);
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

    private <T extends Comparable<T>> void validateWiggleSortable(
        T[] array,
        T median,
        int length,
        int numMedians
    ) {
        double halfLength = length / 2.0;

        if (isOdd(length) && numMedians == ceil(halfLength)) {
            T smallestValue = select(Arrays.asList(array), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest value in the array."
                );
            }
        }

        if (numMedians > ceil(halfLength)) {
            throw new IllegalArgumentException(
                "No more than half the number of values may be the same."
            );
        }
    }

    private boolean isOdd(int value) {
        return (value & 1) == 1;
    }
}