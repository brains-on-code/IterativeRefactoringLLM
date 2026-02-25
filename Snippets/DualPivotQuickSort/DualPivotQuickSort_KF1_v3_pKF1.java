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

    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int lowIndex, final int highIndex) {
        if (lowIndex < highIndex) {
            final int[] pivotPositions = partition(array, lowIndex, highIndex);

            dualPivotQuickSort(array, lowIndex, pivotPositions[0] - 1);
            dualPivotQuickSort(array, pivotPositions[0] + 1, pivotPositions[1] - 1);
            dualPivotQuickSort(array, pivotPositions[1] + 1, highIndex);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int leftIndex, final int rightIndex) {
        if (SortUtils.greater(array[leftIndex], array[rightIndex])) {
            SortUtils.swap(array, leftIndex, rightIndex);
        }

        final T leftPivot = array[leftIndex];
        final T rightPivot = array[rightIndex];

        int leftRegionBoundary = leftIndex + 1;
        int scanIndex = leftIndex + 1;
        int rightRegionBoundary = rightIndex - 1;

        while (scanIndex <= rightRegionBoundary) {
            if (SortUtils.less(array[scanIndex], leftPivot)) {
                SortUtils.swap(array, scanIndex, leftRegionBoundary);
                leftRegionBoundary++;
            } else if (SortUtils.greaterOrEqual(array[scanIndex], rightPivot)) {
                while (scanIndex < rightRegionBoundary && SortUtils.greater(array[rightRegionBoundary], rightPivot)) {
                    rightRegionBoundary--;
                }
                SortUtils.swap(array, scanIndex, rightRegionBoundary);
                rightRegionBoundary--;

                if (SortUtils.less(array[scanIndex], leftPivot)) {
                    SortUtils.swap(array, scanIndex, leftRegionBoundary);
                    leftRegionBoundary++;
                }
            }
            scanIndex++;
        }

        leftRegionBoundary--;
        rightRegionBoundary++;

        SortUtils.swap(array, leftIndex, leftRegionBoundary);
        SortUtils.swap(array, rightIndex, rightRegionBoundary);

        return new int[] {leftRegionBoundary, rightRegionBoundary};
    }
}