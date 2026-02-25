package com.thealgorithms.sorts;

/**
 * Dual Pivot Quick Sort Algorithm.
 *
 * @author Debasish Biswas (https://github.com/debasishbsws)
 * @see SortAlgorithm
 */
public class DualPivotQuickSort implements SortAlgorithm {

    /**
     * Sorts an array using the Dual Pivot QuickSort algorithm.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length <= 1) {
            return array;
        }

        dualPivotQuicksort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively applies Dual Pivot QuickSort to the subarray [left, right].
     *
     * @param array the array to be sorted
     * @param left  the starting index of the subarray
     * @param right the ending index of the subarray
     * @param <T>   the type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void dualPivotQuicksort(
        final T[] array,
        final int left,
        final int right
    ) {
        if (left >= right) {
            return;
        }

        final int[] pivotIndices = partition(array, left, right);

        dualPivotQuicksort(array, left, pivotIndices[0] - 1);
        dualPivotQuicksort(array, pivotIndices[0] + 1, pivotIndices[1] - 1);
        dualPivotQuicksort(array, pivotIndices[1] + 1, right);
    }

    /**
     * Partitions the subarray [left, right] into three regions using two pivots:
     *
     * <pre>
     * [left,  pivot1Index - 1]                elements < pivot1
     * [pivot1Index + 1, pivot2Index - 1]      pivot1 <= elements <= pivot2
     * [pivot2Index + 1, right]                elements > pivot2
     * </pre>
     *
     * @param array the array to be partitioned
     * @param left  the starting index for partitioning
     * @param right the ending index for partitioning
     * @param <T>   the type of elements in the array, which must be comparable
     * @return an array of size 2 containing the final indices of the two pivots:
     *         [pivot1Index, pivot2Index]
     */
    private static <T extends Comparable<T>> int[] partition(
        final T[] array,
        int left,
        final int right
    ) {
        // Ensure array[left] <= array[right] so pivot1 <= pivot2
        if (SortUtils.greater(array[left], array[right])) {
            SortUtils.swap(array, left, right);
        }

        final T pivot1 = array[left];
        final T pivot2 = array[right];

        // Invariant during partitioning:
        // [left + 1, pivot1End - 1]      elements < pivot1
        // [pivot1End, low - 1]           pivot1 <= elements <= pivot2
        // [low, high]                    unclassified elements
        // [high + 1, right - 1]          elements > pivot2
        int pivot1End = left + 1;
        int low = left + 1;
        int high = right - 1;

        while (low <= high) {
            if (SortUtils.less(array[low], pivot1)) {
                SortUtils.swap(array, low, pivot1End);
                pivot1End++;
            } else if (SortUtils.greaterOrEqual(array[low], pivot2)) {
                while (low < high && SortUtils.greater(array[high], pivot2)) {
                    high--;
                }
                SortUtils.swap(array, low, high);
                high--;

                if (SortUtils.less(array[low], pivot1)) {
                    SortUtils.swap(array, low, pivot1End);
                    pivot1End++;
                }
            }
            low++;
        }

        // Move pivots to their final positions
        pivot1End--;
        high++;

        SortUtils.swap(array, left, pivot1End);
        SortUtils.swap(array, right, high);

        return new int[] {pivot1End, high};
    }
}