package com.thealgorithms.sorts;

/**
 * Three-way quicksort implementation using dual pivots.
 */
public class Class1 implements SortAlgorithm {

    /**
     * Sorts the given array using dual-pivot quicksort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] method1(final T[] array) {
        if (array.length <= 1) {
            return array;
        }

        dualPivotQuickSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively applies dual-pivot quicksort to the subarray.
     *
     * @param array the array to be sorted
     * @param left  the left index of the subarray
     * @param right the right index of the subarray
     * @param <T>   the type of elements, must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int left, final int right) {
        if (left < right) {
            final int[] pivots = partition(array, left, right);

            dualPivotQuickSort(array, left, pivots[0] - 1);
            dualPivotQuickSort(array, pivots[0] + 1, pivots[1] - 1);
            dualPivotQuickSort(array, pivots[1] + 1, right);
        }
    }

    /**
     * Partitions the array into three parts using two pivots:
     * elements less than the first pivot, elements between the pivots,
     * and elements greater than the second pivot.
     *
     * @param array the array to partition
     * @param left  the left index
     * @param right the right index
     * @param <T>   the type of elements, must be comparable
     * @return an array of two integers representing the final positions of the pivots
     */
    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int less = left + 1;
        int current = left + 1;
        int greater = right - 1;

        while (current <= greater) {
            if (SortUtils.less(array[current], leftPivot)) {
                SortUtils.swap(array, current, less);
                less++;
            } else if (SortUtils.greaterOrEqual(array[current], rightPivot)) {
                while (current < greater && SortUtils.greater(array[greater], rightPivot)) {
                    greater--;
                }
                SortUtils.swap(array, current, greater);
                greater--;

                if (SortUtils.less(array[current], leftPivot)) {
                    SortUtils.swap(array, current, less);
                    less++;
                }
            }
            current++;
        }

        less--;
        greater++;

        SortUtils.swap(array, left, less);
        SortUtils.swap(array, right, greater);

        return new int[] {less, greater};
    }
}