package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

/**
 * Wiggle sort implementation based on John L.'s answer:
 * https://cs.stackexchange.com/questions/125372/how-to-wiggle-sort-an-array-in-linear-time-complexity
 *
 * Not all arrays are wiggle-sortable. This algorithm detects some obviously
 * non-wiggle-sortable arrays and throws an error, but there are edge cases
 * that are not caught (for example: [1, 2, 2]).
 */
public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return wiggleSort(unsorted);
    }

    /**
     * Index-mapping function used to virtually rearrange indices so that
     * larger elements are placed at odd indices and smaller elements at
     * even indices after tri-color partitioning.
     *
     * @param index logical index
     * @param n     array length
     * @return mapped index in the underlying array
     */
    private int mapIndex(int index, int n) {
        return (2 * index + 1) % (n | 1);
    }

    /**
     * Dutch National Flag–style partitioning around the median using the
     * virtual index mapping. After this procedure:
     * - elements greater than median are grouped at the "front" (odd indices),
     * - elements equal to median are in the "middle",
     * - elements smaller than median are at the "back" (even indices).
     *
     * @param sortThis array to partition
     * @param median   pivot value defining the three groups
     * @param <T>      element type, must be Comparable
     */
    private <T extends Comparable<T>> void triColorSort(T[] sortThis, T median) {
        int n = sortThis.length;
        int i = 0;
        int j = 0;
        int k = n - 1;

        while (j <= k) {
            int mappedJ = mapIndex(j, n);
            int cmp = sortThis[mappedJ].compareTo(median);

            if (cmp > 0) {
                SortUtils.swap(sortThis, mappedJ, mapIndex(i, n));
                i++;
                j++;
            } else if (cmp < 0) {
                SortUtils.swap(sortThis, mappedJ, mapIndex(k, n));
                k--;
            } else {
                j++;
            }
        }
    }

    /**
     * Wiggle-sorts the given array in-place.
     *
     * Steps:
     * 1. Find the median using QuickSelect.
     * 2. Validate that the array is wiggle-sortable under the constraints
     *    described in John L.'s answer.
     * 3. Apply tri-color partitioning with virtual indexing.
     *
     * @param sortThis array to wiggle-sort
     * @param <T>      element type, must be Comparable
     * @return the same array instance, wiggle-sorted
     */
    private <T extends Comparable<T>> T[] wiggleSort(T[] sortThis) {
        // Find the median using QuickSelect.
        T median = select(Arrays.asList(sortThis), (int) floor(sortThis.length / 2.0));

        int numMedians = 0;
        for (T value : sortThis) {
            if (value.compareTo(median) == 0) {
                numMedians++;
            }
        }

        // For odd-length arrays: if the median appears ceil(n/2) times,
        // then the median must be the smallest value in the array.
        // See:
        // https://cs.stackexchange.com/questions/150886/how-to-find-wiggle-sortable-arrays-did-i-misunderstand-john-l-s-answer
        if (sortThis.length % 2 == 1 && numMedians == ceil(sortThis.length / 2.0)) {
            T smallestValue = select(Arrays.asList(sortThis), 0);
            if (smallestValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median must be the smallest value in the array."
                );
            }
        }

        // In any array, no more than half of the elements may be equal to the median.
        if (numMedians > ceil(sortThis.length / 2.0)) {
            throw new IllegalArgumentException(
                "No more than half of the elements may be equal to the same value."
            );
        }

        triColorSort(sortThis, median);
        return sortThis;
    }
}