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
            final int[] pivotIndices = partition(array, lowIndex, highIndex);

            dualPivotQuickSort(array, lowIndex, pivotIndices[0] - 1);
            dualPivotQuickSort(array, pivotIndices[0] + 1, pivotIndices[1] - 1);
            dualPivotQuickSort(array, pivotIndices[1] + 1, highIndex);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int leftIndex, final int rightIndex) {
        if (SortUtils.greater(array[leftIndex], array[rightIndex])) {
            SortUtils.swap(array, leftIndex, rightIndex);
        }

        final T leftPivot = array[leftIndex];
        final T rightPivot = array[rightIndex];

        int lessThanLeftPivotIndex = leftIndex + 1;
        int currentIndex = leftIndex + 1;
        int greaterThanRightPivotIndex = rightIndex - 1;

        while (currentIndex <= greaterThanRightPivotIndex) {
            if (SortUtils.less(array[currentIndex], leftPivot)) {
                SortUtils.swap(array, currentIndex, lessThanLeftPivotIndex);
                lessThanLeftPivotIndex++;
            } else if (SortUtils.greaterOrEqual(array[currentIndex], rightPivot)) {
                while (currentIndex < greaterThanRightPivotIndex && SortUtils.greater(array[greaterThanRightPivotIndex], rightPivot)) {
                    greaterThanRightPivotIndex--;
                }
                SortUtils.swap(array, currentIndex, greaterThanRightPivotIndex);
                greaterThanRightPivotIndex--;

                if (SortUtils.less(array[currentIndex], leftPivot)) {
                    SortUtils.swap(array, currentIndex, lessThanLeftPivotIndex);
                    lessThanLeftPivotIndex++;
                }
            }
            currentIndex++;
        }

        lessThanLeftPivotIndex--;
        greaterThanRightPivotIndex++;

        SortUtils.swap(array, leftIndex, lessThanLeftPivotIndex);
        SortUtils.swap(array, rightIndex, greaterThanRightPivotIndex);

        return new int[] {lessThanLeftPivotIndex, greaterThanRightPivotIndex};
    }
}