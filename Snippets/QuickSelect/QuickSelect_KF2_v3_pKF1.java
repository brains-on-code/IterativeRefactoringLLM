package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class QuickSelect {

    private QuickSelect() {
    }

    public static <T extends Comparable<T>> T select(List<T> list, int targetIndex) {
        Objects.requireNonNull(list, "The list of elements must not be null.");

        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }

        if (targetIndex < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }

        if (targetIndex >= list.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }

        int selectedIndex = selectIndex(list, targetIndex);
        return list.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> list, int targetIndex) {
        return selectIndex(list, 0, list.size() - 1, targetIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(
            List<T> list, int left, int right, int targetIndex) {

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
            List<T> list, int left, int right, int pivotIndex, int targetIndex) {

        T pivotValue = list.get(pivotIndex);
        Collections.swap(list, pivotIndex, right);

        int lessThanPivotBoundary = left;

        for (int currentIndex = left; currentIndex < right; currentIndex++) {
            if (list.get(currentIndex).compareTo(pivotValue) < 0) {
                Collections.swap(list, lessThanPivotBoundary, currentIndex);
                lessThanPivotBoundary++;
            }
        }

        int equalToPivotBoundary = lessThanPivotBoundary;

        for (int currentIndex = lessThanPivotBoundary; currentIndex < right; currentIndex++) {
            if (list.get(currentIndex).compareTo(pivotValue) == 0) {
                Collections.swap(list, equalToPivotBoundary, currentIndex);
                equalToPivotBoundary++;
            }
        }

        Collections.swap(list, right, equalToPivotBoundary);

        return (targetIndex < lessThanPivotBoundary)
                ? lessThanPivotBoundary
                : Math.min(targetIndex, equalToPivotBoundary);
    }

    private static <T extends Comparable<T>> int choosePivotIndex(List<T> list, int left, int right) {
        if (right - left < 5) {
            return medianOfFive(list, left, right);
        }

        for (int groupStart = left; groupStart < right; groupStart += 5) {
            int groupEnd = Math.min(groupStart + 4, right);
            int groupMedianIndex = medianOfFive(list, groupStart, groupEnd);
            int medianDestinationIndex = left + (groupStart - left) / 5;
            Collections.swap(list, groupMedianIndex, medianDestinationIndex);
        }

        int medianOfMediansIndex = (right - left) / 10 + left + 1;
        int lastMedianIndex = left + (right - left) / 5;

        return selectIndex(list, left, lastMedianIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(List<T> list, int left, int right) {
        List<T> subList = list.subList(left, right);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}