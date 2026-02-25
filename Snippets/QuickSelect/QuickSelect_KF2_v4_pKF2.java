package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class QuickSelect {

    private QuickSelect() {
        // Prevent instantiation
    }

    /**
     * Returns the n-th smallest element in the list (0-based index).
     *
     * @param list the list of elements
     * @param n    the order statistic (0-based)
     * @param <T>  element type, must be comparable
     * @return the n-th smallest element
     * @throws NullPointerException      if list is null
     * @throws IllegalArgumentException  if list is empty
     * @throws IndexOutOfBoundsException if n is out of range
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
     * Quickselect on indices [left, right], returning the index of the n-th smallest element.
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
     * Three-way partition around the pivot: [< pivot][== pivot][> pivot].
     *
     * @return an index in the range that still contains the n-th element
     */
    private static <T extends Comparable<T>> int partition(
            List<T> list, int left, int right, int pivotIndex, int n) {

        T pivotValue = list.get(pivotIndex);

        // Move pivot to the end of the current range
        Collections.swap(list, pivotIndex, right);

        int storeIndex = left;

        // First pass: move elements < pivot to the front
        for (int i = left; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(list, storeIndex, i);
                storeIndex++;
            }
        }

        int storeIndexEq = storeIndex;

        // Second pass: group elements == pivot after the < pivot section
        for (int i = storeIndex; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(list, storeIndexEq, i);
                storeIndexEq++;
            }
        }

        // Move pivot into its final position (end of == pivot block)
        Collections.swap(list, right, storeIndexEq);

        // Return an index within the block that still contains the n-th element
        return (n < storeIndex) ? storeIndex : Math.min(n, storeIndexEq);
    }

    /**
     * Selects a pivot using the median-of-medians strategy.
     */
    private static <T extends Comparable<T>> int pivot(List<T> list, int left, int right) {
        // For small ranges, fall back to direct median-of-five
        if (right - left < 5) {
            return partition5(list, left, right);
        }

        // Partition the list into groups of five and move each group's median
        // to the front portion of the list
        for (int i = left; i < right; i += 5) {
            int subRight = Math.min(i + 4, right);
            int median5 = partition5(list, i, subRight);
            int targetIndex = left + (i - left) / 5;
            Collections.swap(list, median5, targetIndex);
        }

        // Compute the median of the medians
        int medianOfMediansTarget = (right - left) / 10 + left + 1;
        int mediansRight = left + (right - left) / 5;

        return selectIndex(list, left, mediansRight, medianOfMediansTarget);
    }

    /**
     * Sorts the sublist [left, right] and returns the index of its median.
     */
    private static <T extends Comparable<T>> int partition5(List<T> list, int left, int right) {
        List<T> subList = list.subList(left, right);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}