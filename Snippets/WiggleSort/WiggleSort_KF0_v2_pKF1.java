package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

/**
 * A wiggle sort implementation based on John L.s' answer in
 * https://cs.stackexchange.com/questions/125372/how-to-wiggle-sort-an-array-in-linear-time-complexity
 * Also have a look at:
 * https://cs.stackexchange.com/questions/125372/how-to-wiggle-sort-an-array-in-linear-time-complexity?noredirect=1&lq=1
 * Not all arrays are wiggle-sortable. This algorithm will find some obviously not wiggle-sortable
 * arrays and throw an error, but there are some exceptions that won't be caught, for example [1, 2,
 * 2].
 */
public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return wiggleSort(array);
    }

    private int getVirtualIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    /**
     * Modified Dutch National Flag Sort. See also: sorts/DutchNationalFlagSort
     *
     * @param array  array to sort into group "greater", "equal" and "smaller" than median
     * @param median defines the groups
     * @param <T>    extends interface Comparable
     */
    private <T extends Comparable<T>> void threeWayPartitionAroundMedian(T[] array, T median) {
        int length = array.length;
        int leftIndex = 0;
        int currentIndex = 0;
        int rightIndex = length - 1;

        while (currentIndex <= rightIndex) {
            int virtualCurrentIndex = getVirtualIndex(currentIndex, length);
            int comparisonResult = array[virtualCurrentIndex].compareTo(median);

            if (comparisonResult > 0) {
                SortUtils.swap(array, virtualCurrentIndex, getVirtualIndex(leftIndex, length));
                leftIndex++;
                currentIndex++;
            } else if (comparisonResult < 0) {
                SortUtils.swap(array, virtualCurrentIndex, getVirtualIndex(rightIndex, length));
                rightIndex--;
            } else {
                currentIndex++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int length = array.length;

        // find the median using quickSelect (if the result isn't in the array, use the next greater value)
        T median = select(Arrays.asList(array), (int) floor(length / 2.0));

        int medianOccurrences = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianOccurrences++;
            }
        }

        // added condition preventing off-by-one errors for odd arrays.
        // https://cs.stackexchange.com/questions/150886/how-to-find-wiggle-sortable-arrays-did-i-misunderstand-john-l-s-answer?noredirect=1&lq=1
        if (length % 2 == 1 && medianOccurrences == ceil(length / 2.0)) {
            T smallestValue = select(Arrays.asList(array), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd Arrays if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest values in the array."
                );
            }
        }

        if (medianOccurrences > ceil(length / 2.0)) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        threeWayPartitionAroundMedian(array, median);
        return array;
    }
}