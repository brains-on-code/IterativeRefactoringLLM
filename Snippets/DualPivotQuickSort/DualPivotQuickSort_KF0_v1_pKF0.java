package com.thealgorithms.sorts;

/**
 * Dual Pivot Quick Sort Algorithm
 *
 * @author Debasish Biswas (https://github.com/debasishbsws)
 * @see SortAlgorithm
 */
public class DualPivotQuickSort implements SortAlgorithm {

    /**
     * Sorts an array using the Dual Pivot QuickSort algorithm.
     *
     * @param array The array to be sorted
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        dualPivotQuickSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively applies the Dual Pivot QuickSort algorithm to subarrays.
     *
     * @param array The array to be sorted
     * @param left  The starting index of the subarray
     * @param right The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return;
        }

        final int[] pivotIndices = partition(array, left, right);

        final int leftPivotIndex = pivotIndices[0];
        final int rightPivotIndex = pivotIndices[1];

        dualPivotQuickSort(array, left, leftPivotIndex - 1);
        dualPivotQuickSort(array, leftPivotIndex + 1, rightPivotIndex - 1);
        dualPivotQuickSort(array, rightPivotIndex + 1, right);
    }

    /**
     * Partitions the array into three parts using two pivots.
     *
     * @param array The array to be partitioned
     * @param left  The starting index for partitioning
     * @param right The ending index for partitioning
     * @param <T>   The type of elements in the array, which must be comparable
     * @return An array containing the indices of the two pivots
     */
    private static <T extends Comparable<T>> int[] partition(final T[] array, final int left, final int right) {
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T leftPivot = array[left];
        final T rightPivot = array[right];

        int lessThanLeftPivotEnd = left + 1;
        int current = left + 1;
        int greaterThanRightPivotStart = right - 1;

        while (current <= greaterThanRightPivotStart) {
            if (SortUtils.less(array[current], leftPivot)) {
                SortUtils.swap(array, current, lessThanLeftPivotEnd);
                lessThanLeftPivotEnd++;
            } else if (SortUtils.greaterOrEqual(array[current], rightPivot)) {
                while (current < greaterThanRightPivotStart && SortUtils.greater(array[greaterThanRightPivotStart], rightPivot)) {
                    greaterThanRightPivotStart--;
                }
                SortUtils.swap(array, current, greaterThanRightPivotStart);
                greaterThanRightPivotStart--;

                if (SortUtils.less(array[current], leftPivot)) {
                    SortUtils.swap(array, current, lessThanLeftPivotEnd);
                    lessThanLeftPivotEnd++;
                }
            }
            current++;
        }

        lessThanLeftPivotEnd--;
        greaterThanRightPivotStart++;

        SortUtils.swap(array, left, lessThanLeftPivotEnd);
        SortUtils.swap(array, right, greaterThanRightPivotStart);

        return new int[] {lessThanLeftPivotEnd, greaterThanRightPivotStart};
    }
}