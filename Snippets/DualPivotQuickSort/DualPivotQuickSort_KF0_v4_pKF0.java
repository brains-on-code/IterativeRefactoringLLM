package com.thealgorithms.sorts;

/**
 * Dual Pivot Quick Sort Algorithm
 *
 * @author Debasish Biswas (https://github.com/debasishbsws)
 * @see SortAlgorithm
 */
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

        final int[] pivotIndices = partition(array, left, right);
        final int leftPivotIndex = pivotIndices[0];
        final int rightPivotIndex = pivotIndices[1];

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
        int current = left + 1;

        while (current <= great) {
            final T currentValue = array[current];

            if (SortUtils.less(currentValue, leftPivot)) {
                SortUtils.swap(array, current, less);
                less++;
                current++;
                continue;
            }

            if (SortUtils.greaterOrEqual(currentValue, rightPivot)) {
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