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
     * Recursively applies dual-pivot quicksort to the subarray [left, right].
     *
     * @param array the array to be sorted
     * @param left  the left index of the subarray (inclusive)
     * @param right the right index of the subarray (inclusive)
     * @param <T>   the type of elements, must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return;
        }

        final int[] pivotPositions = partition(array, left, right);

        dualPivotQuickSort(array, left, pivotPositions[0] - 1);
        dualPivotQuickSort(array, pivotPositions[0] + 1, pivotPositions[1] - 1);
        dualPivotQuickSort(array, pivotPositions[1] + 1, right);
    }

    /**
     * Partitions the array into three regions using two pivots:
     *
     * <pre>
     *   [left,  less - 1]   : elements &lt;  leftPivot
     *   [less,  greater]    : leftPivot ≤ elements ≤ rightPivot
     *   [greater + 1, right]: elements &gt;  rightPivot
     * </pre>
     *
     * @param array the array to partition
     * @param left  the left index (pivot candidate)
     * @param right the right index (pivot candidate)
     * @param <T>   the type of elements, must be comparable
     * @return a 2-element array containing the final positions of the left and right pivots
     */
    private static <T extends Comparable<T>> int[] partition(final T[] array, int left, final int right) {
        // Ensure left pivot <= right pivot
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int less = left + 1;     // next position for element < leftPivot
        int current = left + 1;  // current index being examined
        int greater = right - 1; // next position for element > rightPivot

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

        // Move pivots to their final positions
        less--;
        greater++;

        SortUtils.swap(array, left, less);
        SortUtils.swap(array, right, greater);

        return new int[] {less, greater};
    }
}