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

        final int[] pivots = partition(array, left, right);

        dualPivotQuickSort(array, left, pivots[0] - 1);
        dualPivotQuickSort(array, pivots[0] + 1, pivots[1] - 1);
        dualPivotQuickSort(array, pivots[1] + 1, right);
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        // Ensure pivot1 <= pivot2
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T pivot1 = array[left];
        final T pivot2 = array[right];

        // pivot1End: boundary of elements < pivot1
        int pivot1End = left + 1;
        // low: current index being examined
        int low = left + 1;
        // high: boundary of elements > pivot2
        int high = right - 1;

        while (low <= high) {
            if (SortUtils.less(array[low], pivot1)) {
                SortUtils.swap(array, low, pivot1End);
                pivot1End++;
            } else if (SortUtils.greaterOrEqual(array[low], pivot2)) {
                while (low < high && SortUtils.greater(array[high], pivot2)) {
                    high--;
                }
                SortUtils.swap(array, low, high);
                high--;

                if (SortUtils.less(array[low], pivot1)) {
                    SortUtils.swap(array, low, pivot1End);
                    pivot1End++;
                }
            }
            low++;
        }

        // Move pivots to their final positions
        pivot1End--;
        high++;

        SortUtils.swap(array, left, pivot1End);
        SortUtils.swap(array, right, high);

        // Return final positions of the pivots
        return new int[] {pivot1End, high};
    }
}