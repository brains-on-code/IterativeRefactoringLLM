package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Quickselect implementation using the median-of-medians pivot strategy.
 * <p>
 * This algorithm finds the element that would be at index {@code n} if the list
 * were sorted, without fully sorting the list.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Median_of_medians">Median of medians</a>
 */
public final class QuickSelect {

    private QuickSelect() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the element that would be at index {@code n} if {@code list} were sorted.
     * <p>
     * The order of elements in {@code list} may be modified.
     *
     * @param list the list of elements
     * @param n    the target index (0-based) in the sorted order
     * @param <T>  the type of list elements
     * @return the element that would be at index {@code n} in the sorted list
     * @throws NullPointerException      if {@code list} is {@code null}
     * @throws IllegalArgumentException  if {@code list} is empty
     * @throws IndexOutOfBoundsException if {@code n} is negative or not less than {@code list.size()}
     */
    public static <T extends Comparable<T>> T select(List<T> list, int n) {
        Objects.requireNonNull(list, "The list of elements must not be null.");

        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }

        if (n < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }

        if (n >= list.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }

        int index = selectIndex(list, n);
        return list.get(index);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> list, int n) {
        return selectIndex(list, 0, list.size() - 1, n);
    }

    /**
     * Iteratively selects the index of the n-th smallest element within the subrange
     * {@code [left, right]}.
     */
    private static <T extends Comparable<T>> int selectIndex(List<T> list, int left, int right, int n) {
        while (true) {
            if (left == right) {
                return left;
            }

            int pivotIndex = pivot(list, left, right);
            pivotIndex = partition(list, left, right, pivotIndex, n);

            if (n == pivotIndex) {
                return n;
            } else if (n < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    /**
     * Partitions {@code list[left..right]} around the pivot at {@code pivotIndex}.
     * <p>
     * After partitioning:
     * <ul>
     *   <li>elements &lt; pivot are in {@code [left, storeIndex)}</li>
     *   <li>elements == pivot are in {@code [storeIndex, storeIndexEq]}</li>
     *   <li>elements &gt; pivot are in {@code (storeIndexEq, right]}</li>
     * </ul>
     * The returned index is adjusted so that the caller can continue searching
     * only in the relevant partition for the n-th element.
     */
    private static <T extends Comparable<T>> int partition(
            List<T> list, int left, int right, int pivotIndex, int n) {

        T pivotValue = list.get(pivotIndex);
        Collections.swap(list, pivotIndex, right);

        int storeIndex = left;

        // Move elements smaller than pivot to the beginning
        for (int i = left; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(list, storeIndex, i);
                storeIndex++;
            }
        }

        int storeIndexEq = storeIndex;

        // Group elements equal to pivot after the smaller elements
        for (int i = storeIndex; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(list, storeIndexEq, i);
                storeIndexEq++;
            }
        }

        // Place pivot after the block of equal elements
        Collections.swap(list, right, storeIndexEq);

        // Narrow the search range to the partition that contains index n
        return (n < storeIndex) ? storeIndex : Math.min(n, storeIndexEq);
    }

    /**
     * Chooses a pivot index in {@code list[left..right]} using the median-of-medians
     * strategy for good worst-case performance.
     */
    private static <T extends Comparable<T>> int pivot(List<T> list, int left, int right) {
        // For small ranges, fall back to a simple median-of-five (or fewer) strategy
        if (right - left < 5) {
            return partition5(list, left, right);
        }

        // Partition the range into groups of at most 5 elements and move each group's
        // median to the front of the range.
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            int median5 = partition5(list, i, subRight);
            int targetIndex = left + (i - left) / 5;
            Collections.swap(list, median5, targetIndex);
        }

        // Recursively select the median of the medians as the pivot
        int medOfMedsLeft = left;
        int medOfMedsRight = left + (right - left) / 5;
        int medianOfMediansIndex = (medOfMedsRight - medOfMedsLeft) / 2 + medOfMedsLeft;

        return selectIndex(list, medOfMedsLeft, medOfMedsRight, medianOfMediansIndex);
    }

    /**
     * Sorts the subrange {@code list[left..right]} (inclusive) of at most 5 elements
     * and returns the index of its median.
     */
    private static <T extends Comparable<T>> int partition5(List<T> list, int left, int right) {
        // subList's end index is exclusive, so use right + 1
        List<T> subList = list.subList(left, right + 1);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}