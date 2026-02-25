package com.thealgorithms.sorts;

public class DualPivotQuickSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array == null || array.length <= 1) {
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

        final int leftPivotIndex = pivotPositions[0];
        final int rightPivotIndex = pivotPositions[1];

        dualPivotQuickSort(array, left, leftPivotIndex - 1);
        dualPivotQuickSort(array, leftPivotIndex + 1, rightPivotIndex - 1);
        dualPivotQuickSort(array, rightPivotIndex + 1, right);
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, final int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int lessThanLeftPivotEnd = left + 1;
        int current = left + 1;
        int greaterThanRightPivotStart = right - 1;

        while (current <= greaterThanRightPivotStart) {
            if (SortUtils.less(array[current], leftPivot)) {
                SortUtils.swap(array, current, lessThanLeftPivotEnd);
                lessThanLeftPivotEnd++;
            } else if (SortUtils.greaterOrEqual(array[current], rightPivot)) {
                while (current < greaterThanRightPivotStart && SortUtils.greater(array[greaterThanRightPivotStart], rightPivot)) {
                    greaterThanRightPivotStart--;
                }
                SortUtils.swap(array, current, greaterThanRightPivotStart);
                greaterThanRightPivotStart--;

                if (SortUtils.less(array[current], leftPivot)) {
                    SortUtils.swap(array, current, lessThanLeftPivotEnd);
                    lessThanLeftPivotEnd++;
                }
            }
            current++;
        }

        lessThanLeftPivotEnd--;
        greaterThanRightPivotStart++;

        SortUtils.swap(array, left, lessThanLeftPivotEnd);
        SortUtils.swap(array, right, greaterThanRightPivotStart);

        return new int[] {lessThanLeftPivotEnd, greaterThanRightPivotStart};
    }
}