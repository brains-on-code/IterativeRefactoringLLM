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

    private int mapIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    /**
     * Modified Dutch National Flag Sort. See also: sorts/DutchNationalFlagSort
     *
     * @param array  array to sort into groups "greater", "equal" and "smaller" than median
     * @param median defines the groups
     * @param <T>    extends interface Comparable
     */
    private <T extends Comparable<T>> void triColorSort(T[] array, T median) {
        int n = array.length;
        int left = 0;
        int current = 0;
        int right = n - 1;

        while (current <= right) {
            int mappedCurrent = mapIndex(current, n);
            int comparison = array[mappedCurrent].compareTo(median);

            if (comparison > 0) {
                SortUtils.swap(array, mappedCurrent, mapIndex(left, n));
                left++;
                current++;
            } else if (comparison < 0) {
                SortUtils.swap(array, mappedCurrent, mapIndex(right, n));
                right--;
            } else {
                current++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int n = array.length;

        // find the median using quickSelect
        T median = select(Arrays.asList(array), (int) floor(n / 2.0));

        int numMedians = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                numMedians++;
            }
        }

        // Prevent off-by-one errors for odd-length arrays.
        // https://cs.stackexchange.com/questions/150886/how-to-find-wiggle-sortable-arrays-did-i-misunderstand-john-l-s-answer?noredirect=1&lq=1
        if (n % 2 == 1 && numMedians == ceil(n / 2.0)) {
            T smallestValue = select(Arrays.asList(array), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest value in the array."
                );
            }
        }

        if (numMedians > ceil(n / 2.0)) {
            throw new IllegalArgumentException(
                "No more than half the number of values may be the same."
            );
        }

        triColorSort(array, median);
        return array;
    }
}