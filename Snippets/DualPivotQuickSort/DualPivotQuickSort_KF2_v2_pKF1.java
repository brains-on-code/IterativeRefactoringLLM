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
        int scanIndex = low + 1;
        int rightRegionStart = high - 1;

        while (scanIndex <= rightRegionStart) {
            if (SortUtils.less(array[scanIndex], leftPivot)) {
                SortUtils.swap(array, scanIndex, leftRegionEnd);
                leftRegionEnd++;
            } else if (SortUtils.greaterOrEqual(array[scanIndex], rightPivot)) {
                while (scanIndex < rightRegionStart && SortUtils.greater(array[rightRegionStart], rightPivot)) {
                    rightRegionStart--;
                }
                SortUtils.swap(array, scanIndex, rightRegionStart);
                rightRegionStart--;

                if (SortUtils.less(array[scanIndex], leftPivot)) {
                    SortUtils.swap(array, scanIndex, leftRegionEnd);
                    leftRegionEnd++;
                }
            }
            scanIndex++;
        }

        leftRegionEnd--;
        rightRegionStart++;

        SortUtils.swap(array, low, leftRegionEnd);
        SortUtils.swap(array, high, rightRegionStart);

        return new int[] {leftRegionEnd, rightRegionStart};
    }
}