package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

/**
 * Wiggle sort implementation based on:
 * https://cs.stackexchange.com/questions/125372/how-to-wiggle-sort-an-array-in-linear-time-complexity
 *
 * Not all arrays are wiggle-sortable. This implementation detects some
 * non-wiggle-sortable cases and throws an exception, but does not cover
 * every edge case (e.g., [1, 2, 2]).
 */
public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return wiggleSort(unsorted);
    }

    /**
     * Maps a logical index to a "virtual" index.
     *
     * The mapping is chosen so that, after partitioning:
     * - elements greater than the median occupy odd indices,
     * - elements less than the median occupy even indices.
     *
     * @param index logical index
     * @param n     array length
     * @return mapped index in the underlying array
     */
    private int mapIndex(int index, int n) {
        return (2 * index + 1) % (n | 1);
    }

    /**
     * Three-way partition (Dutch National Flag) around the median using
     * virtual indexing.
     *
     * After this procedure:
     * - elements greater than the median are grouped at the "front"
     *   (odd virtual indices),
     * - elements equal to the median are in the "middle",
     * - elements smaller than the median are at the "back"
     *   (even virtual indices).
     *
     * @param array  array to partition
     * @param median pivot value defining the three groups
     * @param <T>    element type, must be Comparable
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

    /**
     * Wiggle-sorts the given array in-place.
     *
     * Steps:
     * 1. Find the median using QuickSelect.
     * 2. Check necessary wiggle-sortability conditions.
     * 3. Apply three-way partitioning with virtual indexing.
     *
     * @param array array to wiggle-sort
     * @param <T>   element type, must be Comparable
     * @return the same array instance, wiggle-sorted
     */
    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int n = array.length;

        T median = select(Arrays.asList(array), (int) floor(n / 2.0));
        int numMedians = countOccurrences(array, median);

        validateOddLengthArray(array, n, median, numMedians);
        validateMedianFrequency(n, numMedians);

        triColorSort(array, median);
        return array;
    }

    /**
     * Counts how many times the given value appears in the array.
     *
     * @param array array to scan
     * @param value value to count
     * @param <T>   element type, must be Comparable
     * @return number of occurrences of {@code value} in {@code array}
     */
    private <T extends Comparable<T>> int countOccurrences(T[] array, T value) {
        int count = 0;
        for (T element : array) {
            if (element.compareTo(value) == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * For odd-length arrays:
     * If the median appears ceil(n/2) times, then the median must be
     * the smallest value in the array.
     *
     * @param array      array being validated
     * @param n          array length
     * @param median     median value
     * @param numMedians number of occurrences of the median
     * @param <T>        element type, must be Comparable
     */
    private <T extends Comparable<T>> void validateOddLengthArray(
        T[] array,
        int n,
        T median,
        int numMedians
    ) {
        boolean isOddLength = (n % 2 == 1);
        boolean medianAtMaxAllowedFrequency = (numMedians == ceil(n / 2.0));

        if (isOddLength && medianAtMaxAllowedFrequency) {
            T smallestValue = select(Arrays.asList(array), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd-length arrays, if the median appears ceil(n/2) times, "
                        + "the median must be the smallest value in the array."
                );
            }
        }
    }

    /**
     * For any array:
     * No more than half of the elements may be equal to the same value.
     *
     * @param n          array length
     * @param numMedians number of occurrences of the median
     */
    private void validateMedianFrequency(int n, int numMedians) {
        if (numMedians > ceil(n / 2.0)) {
            throw new IllegalArgumentException(
                "No more than half of the elements may be equal to the same value."
            );
        }
    }
}