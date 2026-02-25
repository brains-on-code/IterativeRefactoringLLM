package com.thealgorithms.sorts;

public class DualPivotQuickSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length <= 1) {
            return array;
        }

        dualPivotQuickSort(array, 0, array.length - 1);
        return array;
    }

    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return;
        }

        final int[] pivotPositions = partition(array, left, right);

        dualPivotQuickSort(array, left, pivotPositions[0] - 1);
        dualPivotQuickSort(array, pivotPositions[0] + 1, pivotPositions[1] - 1);
        dualPivotQuickSort(array, pivotPositions[1] + 1, right);
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T pivot1 = array[left];
        final T pivot2 = array[right];

        int lessThanPivot1End = left + 1;
        int current = left + 1;
        int greaterThanPivot2Start = right - 1;

        while (current <= greaterThanPivot2Start) {
            if (SortUtils.less(array[current], pivot1)) {
                SortUtils.swap(array, current, lessThanPivot1End);
                lessThanPivot1End++;
            } else if (SortUtils.greaterOrEqual(array[current], pivot2)) {
                while (current < greaterThanPivot2Start && SortUtils.greater(array[greaterThanPivot2Start], pivot2)) {
                    greaterThanPivot2Start--;
                }
                SortUtils.swap(array, current, greaterThanPivot2Start);
                greaterThanPivot2Start--;

                if (SortUtils.less(array[current], pivot1)) {
                    SortUtils.swap(array, current, lessThanPivot1End);
                    lessThanPivot1End++;
                }
            }
            current++;
        }

        lessThanPivot1End--;
        greaterThanPivot2Start++;

        SortUtils.swap(array, left, lessThanPivot1End);
        SortUtils.swap(array, right, greaterThanPivot2Start);

        return new int[] {lessThanPivot1End, greaterThanPivot2Start};
    }
}