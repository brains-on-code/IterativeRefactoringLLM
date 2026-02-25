package com.thealgorithms.sorts;

/**
 * Dual-pivot quicksort implementation.
 */
public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(final T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        quickSort(array, 0, array.length - 1);
        return array;
    }

    private static <T extends Comparable<T>> void quickSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return;
        }

        final int[] pivotIndices = partition(array, left, right);
        final int leftPivotIndex = pivotIndices[0];
        final int rightPivotIndex = pivotIndices[1];

        quickSort(array, left, leftPivotIndex - 1);
        quickSort(array, leftPivotIndex + 1, rightPivotIndex - 1);
        quickSort(array, rightPivotIndex + 1, right);
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int lessThanLeft = left + 1;
        int scanIndex = left + 1;
        int greaterThanRight = right - 1;

        while (scanIndex <= greaterThanRight) {
            final T current = array[scanIndex];

            if (SortUtils.less(current, leftPivot)) {
                SortUtils.swap(array, scanIndex, lessThanLeft);
                lessThanLeft++;
            } else if (SortUtils.greaterOrEqual(current, rightPivot)) {
                while (scanIndex < greaterThanRight && SortUtils.greater(array[greaterThanRight], rightPivot)) {
                    greaterThanRight--;
                }

                SortUtils.swap(array, scanIndex, greaterThanRight);
                greaterThanRight--;

                if (SortUtils.less(array[scanIndex], leftPivot)) {
                    SortUtils.swap(array, scanIndex, lessThanLeft);
                    lessThanLeft++;
                }
            }

            scanIndex++;
        }

        lessThanLeft--;
        greaterThanRight++;

        SortUtils.swap(array, left, lessThanLeft);
        SortUtils.swap(array, right, greaterThanRight);

        return new int[] { lessThanLeft, greaterThanRight };
    }
}