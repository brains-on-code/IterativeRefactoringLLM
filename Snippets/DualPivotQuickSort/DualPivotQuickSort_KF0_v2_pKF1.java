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
        if (array.length <= 1) {
            return array;
        }

        dualPivotQuickSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively applies the Dual Pivot QuickSort algorithm to subarrays.
     *
     * @param array     The array to be sorted
     * @param start     The starting index of the subarray
     * @param end       The ending index of the subarray
     * @param <T>       The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(
            final T[] array, final int start, final int end) {

        if (start < end) {
            final int[] pivotPositions = partition(array, start, end);

            dualPivotQuickSort(array, start, pivotPositions[0] - 1);
            dualPivotQuickSort(array, pivotPositions[0] + 1, pivotPositions[1] - 1);
            dualPivotQuickSort(array, pivotPositions[1] + 1, end);
        }
    }

    /**
     * Partitions the array into three parts using two pivots.
     *
     * @param array     The array to be partitioned
     * @param start     The starting index for partitioning
     * @param end       The ending index for partitioning
     * @param <T>       The type of elements in the array, which must be comparable
     * @return An array containing the indices of the two pivots
     */
    private static <T extends Comparable<T>> int[] partition(
            final T[] array, int start, final int end) {

        if (SortUtils.greater(array[start], array[end])) {
            SortUtils.swap(array, start, end);
        }

        final T leftPivot = array[start];
        final T rightPivot = array[end];

        int leftRegionEnd = start + 1;
        int scanIndex = start + 1;
        int rightRegionStart = end - 1;

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

        SortUtils.swap(array, start, leftRegionEnd);
        SortUtils.swap(array, end, rightRegionStart);

        return new int[] {leftRegionEnd, rightRegionStart};
    }
}