package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Selection utilities based on the Median of Medians algorithm.
 *
 * <p>Provides a worst-case linear-time method to obtain the k-th smallest
 * element of an unsorted list.</p>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the element at the given zero-based index in the list when the list
     * is conceptually sorted in ascending order.
     *
     * <p>This uses the Median of Medians selection algorithm to achieve
     * worst-case linear time complexity.</p>
     *
     * @param <T>   the element type, which must be {@link Comparable}
     * @param list  the list of elements (must not be {@code null} or empty)
     * @param index the zero-based index of the desired element in sorted order
     * @return the element that would appear at position {@code index} if the list were sorted
     * @throws NullPointerException      if {@code list} is {@code null}
     * @throws IllegalArgumentException  if {@code list} is empty
     * @throws IndexOutOfBoundsException if {@code index} is negative or not less than {@code list.size()}
     */
    public static <T extends Comparable<T>> T method1(List<T> list, int index) {
        Objects.requireNonNull(list, "The list of elements must not be null.");

        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }
        if (index >= list.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }

        int selectedIndex = select(list, index);
        return list.get(selectedIndex);
    }

    /**
     * Returns the index of the element that would be at position {@code index}
     * if the list were sorted.
     */
    private static <T extends Comparable<T>> int select(List<T> list, int index) {
        return select(list, 0, list.size() - 1, index);
    }

    /**
     * Iterative Median of Medians selection on the subrange [{@code left}, {@code right}].
     *
     * @param list  the list
     * @param left  left bound (inclusive)
     * @param right right bound (inclusive)
     * @param index target index in sorted order
     * @return index in {@code list} of the selected element
     */
    private static <T extends Comparable<T>> int select(List<T> list, int left, int right, int index) {
        while (true) {
            if (left == right) {
                return left;
            }

            int pivotIndex = choosePivotIndex(list, left, right);
            pivotIndex = partitionAroundPivot(list, left, right, pivotIndex, index);

            if (index == pivotIndex) {
                return index;
            } else if (index < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    /**
     * Partitions the list around the pivot so that:
     * <ul>
     *   <li>elements less than pivot come first,</li>
     *   <li>then elements equal to pivot,</li>
     *   <li>then elements greater than pivot.</li>
     * </ul>
     *
     * @return an index in the equal-to-pivot range that is guaranteed to contain
     *         the element at {@code index} in sorted order.
     */
    private static <T extends Comparable<T>> int partitionAroundPivot(
            List<T> list, int left, int right, int pivotIndex, int index) {

        T pivotValue = list.get(pivotIndex);
        Collections.swap(list, pivotIndex, right);

        int lessThanEnd = left;

        // Region 1: move elements less than pivot to the front
        for (int i = left; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(list, lessThanEnd, i);
                lessThanEnd++;
            }
        }

        int equalToEnd = lessThanEnd;

        // Region 2: group elements equal to pivot after the "less than" region
        for (int i = lessThanEnd; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(list, equalToEnd, i);
                equalToEnd++;
            }
        }

        // Place pivot at the end of the "equal to" region (Region 3 follows implicitly)
        Collections.swap(list, right, equalToEnd);

        // If the desired index lies in the "less than" region, return its end;
        // otherwise clamp to the end of the "equal to" region.
        return (index < lessThanEnd) ? lessThanEnd : Math.min(index, equalToEnd);
    }

    /**
     * Chooses a pivot index using the Median of Medians strategy.
     *
     * <p>For ranges smaller than 5 elements, returns the median directly.
     * Otherwise, partitions the range into groups of 5, moves each group's
     * median to the front, and recursively selects the median of those medians.</p>
     */
    private static <T extends Comparable<T>> int choosePivotIndex(List<T> list, int left, int right) {
        if (right - left < 5) {
            return medianOfSmallGroup(list, left, right);
        }

        // Partition the list into groups of 5, find each group's median,
        // and move those medians to the front of the list segment.
        for (int groupStart = left; groupStart <= right; groupStart += 5) {
            int groupEnd = Math.min(groupStart + 4, right);
            int medianIndex = medianOfSmallGroup(list, groupStart, groupEnd);
            int medianTarget = left + (groupStart - left) / 5;
            Collections.swap(list, medianIndex, medianTarget);
        }

        int mediansRight = left + (right - left) / 5;
        int medianOfMediansIndex = (mediansRight - left) / 2 + left;
        return select(list, left, mediansRight, medianOfMediansIndex);
    }

    /**
     * Sorts a small sublist and returns the index of its median element.
     *
     * @param list  the list
     * @param left  left bound (inclusive)
     * @param right right bound (inclusive)
     * @return index of the median element within the original list
     */
    private static <T extends Comparable<T>> int medianOfSmallGroup(List<T> list, int left, int right) {
        List<T> subList = list.subList(left, right + 1);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}