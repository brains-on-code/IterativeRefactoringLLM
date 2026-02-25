package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for selection algorithms.
 */
public final class Class1 {

    private Class1() {
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
    public static <T extends Comparable<T>> T method1(List<T> elements, int index) {
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

        int selectedIndex = method3(elements, index);
        return elements.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int method3(List<T> elements, int index) {
        return method3(elements, 0, elements.size() - 1, index);
    }

    private static <T extends Comparable<T>> int method3(List<T> elements, int left, int right, int index) {
        while (true) {
            if (left == right) {
                return left;
            }
            int pivotIndex = method5(elements, left, right);
            pivotIndex = method4(elements, left, right, pivotIndex, index);
            if (index == pivotIndex) {
                return index;
            } else if (index < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int method4(
            List<T> elements,
            int left,
            int right,
            int pivotIndex,
            int index
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, right);
        int lessThanPivotEnd = left;

        for (int i = left; i < right; i++) {
            if (elements.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotEnd, i);
                lessThanPivotEnd++;
            }
        }

        int equalToPivotEnd = lessThanPivotEnd;

        for (int i = lessThanPivotEnd; i < right; i++) {
            if (elements.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotEnd, i);
                equalToPivotEnd++;
            }
        }

        Collections.swap(elements, right, equalToPivotEnd);

        return (index < lessThanPivotEnd) ? lessThanPivotEnd : Math.min(index, equalToPivotEnd);
    }

    private static <T extends Comparable<T>> int method5(List<T> elements, int left, int right) {
        if (right - left < 5) {
            return method6(elements, left, right);
        }

        for (int i = left; i < right; i += 5) {
            int groupRight = i + 4;
            if (groupRight > right) {
                groupRight = right;
            }
            int medianIndex = method6(elements, i, groupRight);
            int medianTargetIndex = left + (i - left) / 5;
            Collections.swap(elements, medianIndex, medianTargetIndex);
        }

        int medianOfMediansIndex = (right - left) / 10 + left + 1;
        int mediansRight = left + (right - left) / 5;
        return method3(elements, left, mediansRight, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int method6(List<T> elements, int left, int right) {
        List<T> subList = elements.subList(left, right);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}