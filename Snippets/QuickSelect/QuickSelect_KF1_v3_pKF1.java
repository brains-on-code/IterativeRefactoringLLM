package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for selection algorithms.
 */
public final class SelectionUtils {

    private SelectionUtils() {
    }

    /**
     * Returns the element at the given index in the list when the list is
     * conceptually sorted, without fully sorting the list.
     *
     * @param elements the list of elements
     * @param index    the zero-based index of the desired element
     * @param <T>      the element type, which must be comparable
     * @return the element that would appear at the given index in a sorted list
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

        int selectedIndex = quickSelectWithMedianOfMedians(elements, index);
        return elements.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int quickSelectWithMedianOfMedians(List<T> elements, int targetIndex) {
        return quickSelectWithMedianOfMedians(elements, 0, elements.size() - 1, targetIndex);
    }

    private static <T extends Comparable<T>> int quickSelectWithMedianOfMedians(
            List<T> elements,
            int leftIndex,
            int rightIndex,
            int targetIndex
    ) {
        while (true) {
            if (leftIndex == rightIndex) {
                return leftIndex;
            }
            int pivotIndex = choosePivotIndexMedianOfMedians(elements, leftIndex, rightIndex);
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
        int lessThanPivotBoundary = leftIndex;

        for (int currentIndex = leftIndex; currentIndex < rightIndex; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotBoundary, currentIndex);
                lessThanPivotBoundary++;
            }
        }

        int equalToPivotBoundary = lessThanPivotBoundary;

        for (int currentIndex = lessThanPivotBoundary; currentIndex < rightIndex; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotBoundary, currentIndex);
                equalToPivotBoundary++;
            }
        }

        Collections.swap(elements, rightIndex, equalToPivotBoundary);

        return (targetIndex < lessThanPivotBoundary)
                ? lessThanPivotBoundary
                : Math.min(targetIndex, equalToPivotBoundary);
    }

    private static <T extends Comparable<T>> int choosePivotIndexMedianOfMedians(
            List<T> elements,
            int leftIndex,
            int rightIndex
    ) {
        if (rightIndex - leftIndex < 5) {
            return medianIndexOfRange(elements, leftIndex, rightIndex);
        }

        for (int groupStart = leftIndex; groupStart < rightIndex; groupStart += 5) {
            int groupEnd = groupStart + 4;
            if (groupEnd > rightIndex) {
                groupEnd = rightIndex;
            }
            int medianIndex = medianIndexOfRange(elements, groupStart, groupEnd);
            int medianPlacementIndex = leftIndex + (groupStart - leftIndex) / 5;
            Collections.swap(elements, medianIndex, medianPlacementIndex);
        }

        int medianOfMediansIndex = (rightIndex - leftIndex) / 10 + leftIndex + 1;
        int mediansRightIndex = leftIndex + (rightIndex - leftIndex) / 5;
        return quickSelectWithMedianOfMedians(elements, leftIndex, mediansRightIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianIndexOfRange(List<T> elements, int leftIndex, int rightIndex) {
        List<T> subList = elements.subList(leftIndex, rightIndex);
        subList.sort(Comparator.naturalOrder());
        return (leftIndex + rightIndex) >>> 1;
    }
}