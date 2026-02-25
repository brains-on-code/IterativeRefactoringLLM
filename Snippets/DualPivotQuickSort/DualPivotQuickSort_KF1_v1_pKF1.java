package com.thealgorithms.sorts;

/**
 * Dual-pivot quicksort implementation.
 */
public class DualPivotQuickSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length <= 1) {
            return array;
        }

        dualPivotQuickSort(array, 0, array.length - 1);
        return array;
    }

    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int low, final int high) {
        if (low < high) {
            final int[] pivotIndices = partition(array, low, high);

            dualPivotQuickSort(array, low, pivotIndices[0] - 1);
            dualPivotQuickSort(array, pivotIndices[0] + 1, pivotIndices[1] - 1);
            dualPivotQuickSort(array, pivotIndices[1] + 1, high);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int less = left + 1;
        int current = left + 1;
        int great = right - 1;

        while (current <= great) {
            if (SortUtils.less(array[current], leftPivot)) {
                SortUtils.swap(array, current, less);
                less++;
            } else if (SortUtils.greaterOrEqual(array[current], rightPivot)) {
                while (current < great && SortUtils.greater(array[great], rightPivot)) {
                    great--;
                }
                SortUtils.swap(array, current, great);
                great--;

                if (SortUtils.less(array[current], leftPivot)) {
                    SortUtils.swap(array, current, less);
                    less++;
                }
            }
            current++;
        }

        less--;
        great++;

        SortUtils.swap(array, left, less);
        SortUtils.swap(array, right, great);

        return new int[] {less, great};
    }
}