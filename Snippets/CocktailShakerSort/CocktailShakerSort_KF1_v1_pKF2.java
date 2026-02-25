package com.thealgorithms.sorts;

/**
 * Implementation of the Cocktail Shaker Sort algorithm.
 *
 * <p>This is a variation of Bubble Sort that sorts in both directions on each
 * pass through the list. It can detect if the list is already sorted and
 * reduce the range of subsequent passes based on the last swap positions.
 */
class Class1 implements SortAlgorithm {

    /**
     * Sorts the given array using Cocktail Shaker Sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, which must be comparable
     * @return the sorted array (sorted in-place and also returned for convenience)
     */
    @Override
    public <T extends Comparable<T>> T[] method1(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            end = sweepForward(array, start, end);
            start = sweepBackward(array, start, end);
        }

        return array;
    }

    /**
     * Performs a forward pass (left to right) over the array segment,
     * bubbling the largest element in the range to the right.
     *
     * @param array the array being sorted
     * @param start the starting index (inclusive) of the pass
     * @param end   the ending index (inclusive) of the pass
     * @param <T>   the type of elements, which must be comparable
     * @return the index of the last swap performed during this pass
     */
    private <T extends Comparable<T>> int sweepForward(final T[] array, final int start, final int end) {
        int lastSwapIndex = start;

        for (int i = start; i < end; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }

    /**
     * Performs a backward pass (right to left) over the array segment,
     * bubbling the smallest element in the range to the left.
     *
     * @param array the array being sorted
     * @param start the starting index (inclusive) of the pass
     * @param end   the ending index (inclusive) of the pass
     * @param <T>   the type of elements, which must be comparable
     * @return the index of the last swap performed during this pass
     */
    private <T extends Comparable<T>> int sweepBackward(final T[] array, final int start, final int end) {
        int lastSwapIndex = end;

        for (int i = end; i > start; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }
}