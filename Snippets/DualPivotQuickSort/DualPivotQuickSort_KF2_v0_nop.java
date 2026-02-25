package com.thealgorithms.sorts;


public class DualPivotQuickSort implements SortAlgorithm {


    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length <= 1) {
            return array;
        }

        dualPivotQuicksort(array, 0, array.length - 1);
        return array;
    }


    private static <T extends Comparable<T>> void dualPivotQuicksort(final T[] array, final int left, final int right) {
        if (left < right) {
            final int[] pivots = partition(array, left, right);

            dualPivotQuicksort(array, left, pivots[0] - 1);
            dualPivotQuicksort(array, pivots[0] + 1, pivots[1] - 1);
            dualPivotQuicksort(array, pivots[1] + 1, right);
        }
    }


    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T pivot1 = array[left];
        final T pivot2 = array[right];

        int pivot1End = left + 1;
        int low = left + 1;
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

        pivot1End--;
        high++;

        SortUtils.swap(array, left, pivot1End);
        SortUtils.swap(array, right, high);

        return new int[] {low, high};
    }
}
