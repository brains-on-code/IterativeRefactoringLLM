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

        int less = left + 1;
        int great = right - 1;

        for (int i = left + 1; i <= great; i++) {
            if (SortUtils.less(array[i], leftPivot)) {
                SortUtils.swap(array, i, less);
                less++;
                continue;
            }

            if (SortUtils.greaterOrEqual(array[i], rightPivot)) {
                while (i < great && SortUtils.greater(array[great], rightPivot)) {
                    great--;
                }

                SortUtils.swap(array, i, great);
                great--;

                if (SortUtils.less(array[i], leftPivot)) {
                    SortUtils.swap(array, i, less);
                    less++;
                }
            }
        }

        less--;
        great++;

        SortUtils.swap(array, left, less);
        SortUtils.swap(array, right, great);

        return new int[] {less, great};
    }
}