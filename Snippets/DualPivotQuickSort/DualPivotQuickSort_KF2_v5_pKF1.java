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

    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int startIndex, final int endIndex) {
        if (startIndex < endIndex) {
            final int[] pivotIndices = partition(array, startIndex, endIndex);

            dualPivotQuickSort(array, startIndex, pivotIndices[0] - 1);
            dualPivotQuickSort(array, pivotIndices[0] + 1, pivotIndices[1] - 1);
            dualPivotQuickSort(array, pivotIndices[1] + 1, endIndex);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int startIndex, final int endIndex) {
        if (SortUtils.greater(array[startIndex], array[endIndex])) {
            SortUtils.swap(array, startIndex, endIndex);
        }

        final T leftPivot = array[startIndex];
        final T rightPivot = array[endIndex];

        int leftPartitionBoundary = startIndex + 1;
        int scanIndex = startIndex + 1;
        int rightPartitionBoundary = endIndex - 1;

        while (scanIndex <= rightPartitionBoundary) {
            if (SortUtils.less(array[scanIndex], leftPivot)) {
                SortUtils.swap(array, scanIndex, leftPartitionBoundary);
                leftPartitionBoundary++;
            } else if (SortUtils.greaterOrEqual(array[scanIndex], rightPivot)) {
                while (scanIndex < rightPartitionBoundary && SortUtils.greater(array[rightPartitionBoundary], rightPivot)) {
                    rightPartitionBoundary--;
                }
                SortUtils.swap(array, scanIndex, rightPartitionBoundary);
                rightPartitionBoundary--;

                if (SortUtils.less(array[scanIndex], leftPivot)) {
                    SortUtils.swap(array, scanIndex, leftPartitionBoundary);
                    leftPartitionBoundary++;
                }
            }
            scanIndex++;
        }

        leftPartitionBoundary--;
        rightPartitionBoundary++;

        SortUtils.swap(array, startIndex, leftPartitionBoundary);
        SortUtils.swap(array, endIndex, rightPartitionBoundary);

        return new int[] {leftPartitionBoundary, rightPartitionBoundary};
    }
}