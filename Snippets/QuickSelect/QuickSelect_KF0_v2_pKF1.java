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
     * Selects the {@code n}-th smallest element of {@code elements}, i.e. the element that would
     * be at index n if the list was sorted.
     * <p>
     * Calling this function might change the order of elements in {@code elements}.
     *
     * @param elements the list of elements
     * @param index    the index (0-based) of the desired element in sorted order
     * @param <T>      the type of list elements
     * @return the n-th smallest element in the list
     * @throws IndexOutOfBoundsException if index is less than 0 or greater or equal to
     *                                   the number of elements in the list
     * @throws IllegalArgumentException  if the list is empty
     * @throws NullPointerException      if {@code elements} is null
     */
    public static <T extends Comparable<T>> T select(List<T> elements, int index) {
        Objects.requireNonNull(elements, "The list of elements must not be null.");

        if (elements.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }

        if (index >= elements.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }

        int selectedIndex = selectIndex(elements, index);
        return elements.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> elements, int targetIndex) {
        return selectIndex(elements, 0, elements.size() - 1, targetIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(
        List<T> elements,
        int leftIndex,
        int rightIndex,
        int targetIndex
    ) {
        while (true) {
            if (leftIndex == rightIndex) {
                return leftIndex;
            }

            int pivotIndex = choosePivotIndex(elements, leftIndex, rightIndex);
            pivotIndex = partitionAroundPivot(elements, leftIndex, rightIndex, pivotIndex, targetIndex);

            if (targetIndex == pivotIndex) {
                return targetIndex;
            } else if (targetIndex < pivotIndex) {
                rightIndex = pivotIndex - 1;
            } else {
                leftIndex = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partitionAroundPivot(
        List<T> elements,
        int leftIndex,
        int rightIndex,
        int pivotIndex,
        int targetIndex
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, rightIndex);

        int smallerElementsBoundary = leftIndex;

        for (int currentIndex = leftIndex; currentIndex < rightIndex; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) < 0) {
                Collections.swap(elements, smallerElementsBoundary, currentIndex);
                smallerElementsBoundary++;
            }
        }

        int equalElementsBoundary = smallerElementsBoundary;

        for (int currentIndex = smallerElementsBoundary; currentIndex < rightIndex; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalElementsBoundary, currentIndex);
                equalElementsBoundary++;
            }
        }

        Collections.swap(elements, rightIndex, equalElementsBoundary);

        if (targetIndex < smallerElementsBoundary) {
            return smallerElementsBoundary;
        }

        return Math.min(targetIndex, equalElementsBoundary);
    }

    private static <T extends Comparable<T>> int choosePivotIndex(
        List<T> elements,
        int leftIndex,
        int rightIndex
    ) {
        if (rightIndex - leftIndex < 5) {
            return medianOfFive(elements, leftIndex, rightIndex);
        }

        for (int groupStartIndex = leftIndex; groupStartIndex < rightIndex; groupStartIndex += 5) {
            int groupEndIndex = groupStartIndex + 4;
            if (groupEndIndex > rightIndex) {
                groupEndIndex = rightIndex;
            }

            int groupMedianIndex = medianOfFive(elements, groupStartIndex, groupEndIndex);
            int medianPlacementIndex = leftIndex + (groupStartIndex - leftIndex) / 5;
            Collections.swap(elements, groupMedianIndex, medianPlacementIndex);
        }

        int medianOfMediansIndex = (rightIndex - leftIndex) / 10 + leftIndex + 1;
        int lastMedianIndex = leftIndex + (rightIndex - leftIndex) / 5;

        return selectIndex(elements, leftIndex, lastMedianIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(
        List<T> elements,
        int leftIndex,
        int rightIndex
    ) {
        List<T> subList = elements.subList(leftIndex, rightIndex);
        subList.sort(Comparator.naturalOrder());
        return (leftIndex + rightIndex) >>> 1;
    }
}