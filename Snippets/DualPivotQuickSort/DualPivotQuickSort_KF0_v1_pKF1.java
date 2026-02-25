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
     * @param leftIndex  The starting index of the subarray
     * @param rightIndex The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(
            final T[] array, final int leftIndex, final int rightIndex) {

        if (leftIndex < rightIndex) {
            final int[] pivotIndices = partition(array, leftIndex, rightIndex);

            dualPivotQuickSort(array, leftIndex, pivotIndices[0] - 1);
            dualPivotQuickSort(array, pivotIndices[0] + 1, pivotIndices[1] - 1);
            dualPivotQuickSort(array, pivotIndices[1] + 1, rightIndex);
        }
    }

    /**
     * Partitions the array into three parts using two pivots.
     *
     * @param array The array to be partitioned
     * @param leftIndex  The starting index for partitioning
     * @param rightIndex The ending index for partitioning
     * @param <T>   The type of elements in the array, which must be comparable
     * @return An array containing the indices of the two pivots
     */
    private static <T extends Comparable<T>> int[] partition(
            final T[] array, int leftIndex, final int rightIndex) {

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