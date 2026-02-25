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
     * @param low   The starting index of the subarray
     * @param high  The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuickSort(
            final T[] array, final int low, final int high) {

        if (low < high) {
            final int[] pivotIndices = partition(array, low, high);

            final int leftPivotIndex = pivotIndices[0];
            final int rightPivotIndex = pivotIndices[1];

            dualPivotQuickSort(array, low, leftPivotIndex - 1);
            dualPivotQuickSort(array, leftPivotIndex + 1, rightPivotIndex - 1);
            dualPivotQuickSort(array, rightPivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array into three parts using two pivots.
     *
     * @param array The array to be partitioned
     * @param low   The starting index for partitioning
     * @param high  The ending index for partitioning
     * @param <T>   The type of elements in the array, which must be comparable
     * @return An array containing the indices of the two pivots
     */
    private static <T extends Comparable<T>> int[] partition(
            final T[] array, int low, final int high) {

        if (SortUtils.greater(array[low], array[high])) {
            SortUtils.swap(array, low, high);
        }

        final T leftPivot = array[low];
        final T rightPivot = array[high];

        int lessThanLeftPivotBoundary = low + 1;
        int currentIndex = low + 1;
        int greaterThanRightPivotBoundary = high - 1;

        while (currentIndex <= greaterThanRightPivotBoundary) {
            if (SortUtils.less(array[currentIndex], leftPivot)) {
                SortUtils.swap(array, currentIndex, lessThanLeftPivotBoundary);
                lessThanLeftPivotBoundary++;
            } else if (SortUtils.greaterOrEqual(array[currentIndex], rightPivot)) {
                while (currentIndex < greaterThanRightPivotBoundary
                        && SortUtils.greater(array[greaterThanRightPivotBoundary], rightPivot)) {
                    greaterThanRightPivotBoundary--;
                }
                SortUtils.swap(array, currentIndex, greaterThanRightPivotBoundary);
                greaterThanRightPivotBoundary--;

                if (SortUtils.less(array[currentIndex], leftPivot)) {
                    SortUtils.swap(array, currentIndex, lessThanLeftPivotBoundary);
                    lessThanLeftPivotBoundary++;
                }
            }
            currentIndex++;
        }

        lessThanLeftPivotBoundary--;
        greaterThanRightPivotBoundary++;

        SortUtils.swap(array, low, lessThanLeftPivotBoundary);
        SortUtils.swap(array, high, greaterThanRightPivotBoundary);

        return new int[] {lessThanLeftPivotBoundary, greaterThanRightPivotBoundary};
    }
}