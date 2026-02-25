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

    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int leftIndex, final int rightIndex) {
        if (leftIndex < rightIndex) {
            final int[] pivotIndices = partition(array, leftIndex, rightIndex);

            dualPivotQuickSort(array, leftIndex, pivotIndices[0] - 1);
            dualPivotQuickSort(array, pivotIndices[0] + 1, pivotIndices[1] - 1);
            dualPivotQuickSort(array, pivotIndices[1] + 1, rightIndex);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int leftIndex, final int rightIndex) {
        if (SortUtils.greater(array[leftIndex], array[rightIndex])) {
            SortUtils.swap(array, leftIndex, rightIndex);
        }

        final T leftPivot = array[leftIndex];
        final T rightPivot = array[rightIndex];

        int leftPivotBoundary = leftIndex + 1;
        int currentIndex = leftIndex + 1;
        int rightPivotBoundary = rightIndex - 1;

        while (currentIndex <= rightPivotBoundary) {
            if (SortUtils.less(array[currentIndex], leftPivot)) {
                SortUtils.swap(array, currentIndex, leftPivotBoundary);
                leftPivotBoundary++;
            } else if (SortUtils.greaterOrEqual(array[currentIndex], rightPivot)) {
                while (currentIndex < rightPivotBoundary && SortUtils.greater(array[rightPivotBoundary], rightPivot)) {
                    rightPivotBoundary--;
                }
                SortUtils.swap(array, currentIndex, rightPivotBoundary);
                rightPivotBoundary--;

                if (SortUtils.less(array[currentIndex], leftPivot)) {
                    SortUtils.swap(array, currentIndex, leftPivotBoundary);
                    leftPivotBoundary++;
                }
            }
            currentIndex++;
        }

        leftPivotBoundary--;
        rightPivotBoundary++;

        SortUtils.swap(array, leftIndex, leftPivotBoundary);
        SortUtils.swap(array, rightIndex, rightPivotBoundary);

        return new int[] {leftPivotBoundary, rightPivotBoundary};
    }
}