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
     * @param array The array to be sorted
     * @param startIndex   The starting index of the subarray
     * @param endIndex  The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(
            final T[] array, final int startIndex, final int endIndex) {

        if (startIndex < endIndex) {
            final int[] pivotIndices = partition(array, startIndex, endIndex);

            final int leftPivotIndex = pivotIndices[0];
            final int rightPivotIndex = pivotIndices[1];

            dualPivotQuickSort(array, startIndex, leftPivotIndex - 1);
            dualPivotQuickSort(array, leftPivotIndex + 1, rightPivotIndex - 1);
            dualPivotQuickSort(array, rightPivotIndex + 1, endIndex);
        }
    }

    /**
     * Partitions the array into three parts using two pivots.
     *
     * @param array The array to be partitioned
     * @param startIndex   The starting index for partitioning
     * @param endIndex  The ending index for partitioning
     * @param <T>   The type of elements in the array, which must be comparable
     * @return An array containing the indices of the two pivots
     */
    private static <T extends Comparable<T>> int[] partition(
            final T[] array, int startIndex, final int endIndex) {

        if (SortUtils.greater(array[startIndex], array[endIndex])) {
            SortUtils.swap(array, startIndex, endIndex);
        }

        final T leftPivot = array[startIndex];
        final T rightPivot = array[endIndex];

        int leftRegionBoundary = startIndex + 1;
        int scanIndex = startIndex + 1;
        int rightRegionBoundary = endIndex - 1;

        while (scanIndex <= rightRegionBoundary) {
            if (SortUtils.less(array[scanIndex], leftPivot)) {
                SortUtils.swap(array, scanIndex, leftRegionBoundary);
                leftRegionBoundary++;
            } else if (SortUtils.greaterOrEqual(array[scanIndex], rightPivot)) {
                while (scanIndex < rightRegionBoundary
                        && SortUtils.greater(array[rightRegionBoundary], rightPivot)) {
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

        SortUtils.swap(array, startIndex, leftRegionBoundary);
        SortUtils.swap(array, endIndex, rightRegionBoundary);

        return new int[] {leftRegionBoundary, rightRegionBoundary};
    }
}