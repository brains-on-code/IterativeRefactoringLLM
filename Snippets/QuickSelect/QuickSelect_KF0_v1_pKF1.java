package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of the Quickselect algorithm as described
 * <a href="https://en.wikipedia.org/wiki/Median_of_medians">here</a>.
 */
public final class QuickSelect {
    private QuickSelect() {
    }

    /**
     * Selects the {@code n}-th largest element of {@code list}, i.e. the element that would
     * be at index n if the list was sorted.
     * <p>
     * Calling this function might change the order of elements in {@code list}.
     *
     * @param list the list of elements
     * @param n    the index
     * @param <T>  the type of list elements
     * @return the n-th largest element in the list
     * @throws IndexOutOfBoundsException if n is less than 0 or greater or equal to
     *                                   the number of elements in the list
     * @throws IllegalArgumentException  if the list is empty
     * @throws NullPointerException      if {@code list} is null
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

        int selectedIndex = selectIndex(list, n);
        return list.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> list, int targetIndex) {
        return selectIndex(list, 0, list.size() - 1, targetIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> list, int left, int right, int targetIndex) {
        while (true) {
            if (left == right) {
                return left;
            }
            int pivotIndex = choosePivotIndex(list, left, right);
            pivotIndex = partitionAroundPivot(list, left, right, pivotIndex, targetIndex);
            if (targetIndex == pivotIndex) {
                return targetIndex;
            } else if (targetIndex < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partitionAroundPivot(
        List<T> list,
        int left,
        int right,
        int pivotIndex,
        int targetIndex
    ) {
        T pivotValue = list.get(pivotIndex);
        Collections.swap(list, pivotIndex, right);
        int storeIndex = left;

        for (int currentIndex = left; currentIndex < right; currentIndex++) {
            if (list.get(currentIndex).compareTo(pivotValue) < 0) {
                Collections.swap(list, storeIndex, currentIndex);
                storeIndex++;
            }
        }

        int equalPivotStartIndex = storeIndex;

        for (int currentIndex = storeIndex; currentIndex < right; currentIndex++) {
            if (list.get(currentIndex).compareTo(pivotValue) == 0) {
                Collections.swap(list, equalPivotStartIndex, currentIndex);
                equalPivotStartIndex++;
            }
        }

        Collections.swap(list, right, equalPivotStartIndex);

        return (targetIndex < storeIndex) ? storeIndex : Math.min(targetIndex, equalPivotStartIndex);
    }

    private static <T extends Comparable<T>> int choosePivotIndex(List<T> list, int left, int right) {
        if (right - left < 5) {
            return medianOfFive(list, left, right);
        }

        for (int groupStart = left; groupStart < right; groupStart += 5) {
            int groupEnd = groupStart + 4;
            if (groupEnd > right) {
                groupEnd = right;
            }
            int medianOfGroupIndex = medianOfFive(list, groupStart, groupEnd);
            int medianTargetIndex = left + (groupStart - left) / 5;
            Collections.swap(list, medianOfGroupIndex, medianTargetIndex);
        }

        int medianOfMediansTargetIndex = (right - left) / 10 + left + 1;
        int lastMedianIndex = left + (right - left) / 5;
        return selectIndex(list, left, lastMedianIndex, medianOfMediansTargetIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(List<T> list, int left, int right) {
        List<T> subList = list.subList(left, right);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}