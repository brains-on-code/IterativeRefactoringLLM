package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class QuickSelect {

    private QuickSelect() {
    }

    public static <T extends Comparable<T>> T select(List<T> elements, int targetIndex) {
        Objects.requireNonNull(elements, "The list of elements must not be null.");

        if (elements.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }

        if (targetIndex < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }

        if (targetIndex >= elements.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }

        int selectedIndex = selectIndex(elements, targetIndex);
        return elements.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> elements, int targetIndex) {
        return selectIndex(elements, 0, elements.size() - 1, targetIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(
            List<T> elements, int leftIndex, int rightIndex, int targetIndex) {

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
            List<T> elements, int leftIndex, int rightIndex, int pivotIndex, int targetIndex) {

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

    private static <T extends Comparable<T>> int choosePivotIndex(List<T> elements, int leftIndex, int rightIndex) {
        if (rightIndex - leftIndex < 5) {
            return medianOfFive(elements, leftIndex, rightIndex);
        }

        for (int groupStartIndex = leftIndex; groupStartIndex < rightIndex; groupStartIndex += 5) {
            int groupEndIndex = Math.min(groupStartIndex + 4, rightIndex);
            int groupMedianIndex = medianOfFive(elements, groupStartIndex, groupEndIndex);
            int medianDestinationIndex = leftIndex + (groupStartIndex - leftIndex) / 5;
            Collections.swap(elements, groupMedianIndex, medianDestinationIndex);
        }

        int medianOfMediansIndex = (rightIndex - leftIndex) / 10 + leftIndex + 1;
        int lastMedianIndex = leftIndex + (rightIndex - leftIndex) / 5;

        return selectIndex(elements, leftIndex, lastMedianIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(List<T> elements, int leftIndex, int rightIndex) {
        List<T> subList = elements.subList(leftIndex, rightIndex);
        subList.sort(Comparator.naturalOrder());
        return (leftIndex + rightIndex) >>> 1;
    }
}