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
        if (left < right) {
            final int[] pivotBounds = partition(array, left, right);

            quickSort(array, left, pivotBounds[0] - 1);
            quickSort(array, pivotBounds[0] + 1, pivotBounds[1] - 1);
            quickSort(array, pivotBounds[1] + 1, right);
        }
    }

    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int less = left + 1;
        int current = left + 1;
        int great = right - 1;

        while (current <= great) {
            if (SortUtils.less(array[current], leftPivot)) {
                SortUtils.swap(array, current, less);
                less++;
            } else if (SortUtils.greaterOrEqual(array[current], rightPivot)) {
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