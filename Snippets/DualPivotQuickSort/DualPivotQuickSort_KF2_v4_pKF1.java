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

    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int low, final int high) {
        if (low < high) {
            final int[] pivotPositions = partition(array, low, high);

            dualPivotQuickSort(array, low, pivotPositions[0] - 1);
            dualPivotQuickSort(array, pivotPositions[0] + 1, pivotPositions[1] - 1);
            dualPivotQuickSort(array, pivotPositions[1] + 1, high);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int low, final int high) {
        if (SortUtils.greater(array[low], array[high])) {
            SortUtils.swap(array, low, high);
        }

        final T leftPivot = array[low];
        final T rightPivot = array[high];

        int leftRegionEnd = low + 1;
        int current = low + 1;
        int rightRegionStart = high - 1;

        while (current <= rightRegionStart) {
            if (SortUtils.less(array[current], leftPivot)) {
                SortUtils.swap(array, current, leftRegionEnd);
                leftRegionEnd++;
            } else if (SortUtils.greaterOrEqual(array[current], rightPivot)) {
                while (current < rightRegionStart && SortUtils.greater(array[rightRegionStart], rightPivot)) {
                    rightRegionStart--;
                }
                SortUtils.swap(array, current, rightRegionStart);
                rightRegionStart--;

                if (SortUtils.less(array[current], leftPivot)) {
                    SortUtils.swap(array, current, leftRegionEnd);
                    leftRegionEnd++;
                }
            }
            current++;
        }

        leftRegionEnd--;
        rightRegionStart++;

        SortUtils.swap(array, low, leftRegionEnd);
        SortUtils.swap(array, high, rightRegionStart);

        return new int[] {leftRegionEnd, rightRegionStart};
    }
}