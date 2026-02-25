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
            int leftBound,
            int rightBound,
            int targetIndex
    ) {
        int left = leftBound;
        int right = rightBound;

        while (true) {
            if (left == right) {
                return left;
            }

            int pivotIndex = choosePivotIndexMedianOfMedians(elements, left, right);
            pivotIndex = partitionAroundPivot(elements, left, right, pivotIndex, targetIndex);

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
            List<T> elements,
            int leftBound,
            int rightBound,
            int pivotIndex,
            int targetIndex
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, rightBound);

        int lessThanPivotEnd = leftBound;

        for (int currentIndex = leftBound; currentIndex < rightBound; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotEnd, currentIndex);
                lessThanPivotEnd++;
            }
        }

        int equalToPivotEnd = lessThanPivotEnd;

        for (int currentIndex = lessThanPivotEnd; currentIndex < rightBound; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotEnd, currentIndex);
                equalToPivotEnd++;
            }
        }

        Collections.swap(elements, rightBound, equalToPivotEnd);

        return (targetIndex < lessThanPivotEnd)
                ? lessThanPivotEnd
                : Math.min(targetIndex, equalToPivotEnd);
    }

    private static <T extends Comparable<T>> int choosePivotIndexMedianOfMedians(
            List<T> elements,
            int leftBound,
            int rightBound
    ) {
        if (rightBound - leftBound < 5) {
            return medianIndexOfRange(elements, leftBound, rightBound);
        }

        for (int groupStart = leftBound; groupStart < rightBound; groupStart += 5) {
            int groupEnd = groupStart + 4;
            if (groupEnd > rightBound) {
                groupEnd = rightBound;
            }
            int medianIndex = medianIndexOfRange(elements, groupStart, groupEnd);
            int medianPlacementIndex = leftBound + (groupStart - leftBound) / 5;
            Collections.swap(elements, medianIndex, medianPlacementIndex);
        }

        int medianOfMediansIndex = (rightBound - leftBound) / 10 + leftBound + 1;
        int mediansRightBound = leftBound + (rightBound - leftBound) / 5;
        return quickSelectWithMedianOfMedians(elements, leftBound, mediansRightBound, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianIndexOfRange(List<T> elements, int leftBound, int rightBound) {
        List<T> subList = elements.subList(leftBound, rightBound);
        subList.sort(Comparator.naturalOrder());
        return (leftBound + rightBound) >>> 1;
    }
}