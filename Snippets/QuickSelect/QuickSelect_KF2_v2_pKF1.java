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
            List<T> elements, int leftBound, int rightBound, int targetIndex) {

        while (true) {
            if (leftBound == rightBound) {
                return leftBound;
            }

            int pivotIndex = choosePivotIndex(elements, leftBound, rightBound);
            pivotIndex = partitionAroundPivot(elements, leftBound, rightBound, pivotIndex, targetIndex);

            if (targetIndex == pivotIndex) {
                return targetIndex;
            } else if (targetIndex < pivotIndex) {
                rightBound = pivotIndex - 1;
            } else {
                leftBound = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partitionAroundPivot(
            List<T> elements, int leftBound, int rightBound, int pivotIndex, int targetIndex) {

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

    private static <T extends Comparable<T>> int choosePivotIndex(List<T> elements, int leftBound, int rightBound) {
        if (rightBound - leftBound < 5) {
            return medianOfFive(elements, leftBound, rightBound);
        }

        for (int groupStartIndex = leftBound; groupStartIndex < rightBound; groupStartIndex += 5) {
            int groupEndIndex = Math.min(groupStartIndex + 4, rightBound);
            int groupMedianIndex = medianOfFive(elements, groupStartIndex, groupEndIndex);
            int medianPlacementIndex = leftBound + (groupStartIndex - leftBound) / 5;
            Collections.swap(elements, groupMedianIndex, medianPlacementIndex);
        }

        int medianOfMediansIndex = (rightBound - leftBound) / 10 + leftBound + 1;
        int lastMedianIndex = leftBound + (rightBound - leftBound) / 5;

        return selectIndex(elements, leftBound, lastMedianIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(List<T> elements, int leftBound, int rightBound) {
        List<T> subList = elements.subList(leftBound, rightBound);
        subList.sort(Comparator.naturalOrder());
        return (leftBound + rightBound) >>> 1;
    }
}