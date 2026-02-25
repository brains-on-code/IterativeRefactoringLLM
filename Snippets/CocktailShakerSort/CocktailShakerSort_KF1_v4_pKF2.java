package com.thealgorithms.sorts;

/**
 * Cocktail Shaker Sort implementation.
 *
 * <p>A bidirectional variant of Bubble Sort that:
 * <ul>
 *   <li>Moves the largest element to the end in a forward pass.</li>
 *   <li>Moves the smallest element to the beginning in a backward pass.</li>
 *   <li>Shrinks the unsorted range using the last swap positions.</li>
 * </ul>
 */
class CocktailShakerSort implements SortAlgorithm {

    /**
     * Sorts the given array using Cocktail Shaker Sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, which must be comparable
     * @return the same array instance, sorted in-place
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
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
     * Forward pass (left to right) over [start, end].
     * Places the largest element in this range at its correct position
     * near the right side and returns the index of the last swap.
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
     * Backward pass (right to left) over [start, end].
     * Places the smallest element in this range at its correct position
     * near the left side and returns the index of the last swap.
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