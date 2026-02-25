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
     * Performs a forward pass from {@code start} to {@code end - 1}.
     * Swaps adjacent out-of-order elements so that the largest element
     * in this range moves toward the right. Returns the index of the
     * last swap, which becomes the new upper bound of the unsorted range.
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
     * Performs a backward pass from {@code end} to {@code start + 1}.
     * Swaps adjacent out-of-order elements so that the smallest element
     * in this range moves toward the left. Returns the index of the
     * last swap, which becomes the new lower bound of the unsorted range.
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