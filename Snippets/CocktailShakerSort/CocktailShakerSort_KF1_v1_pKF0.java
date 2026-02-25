package com.thealgorithms.sorts;

/**
 * Implementation of the Cocktail Shaker Sort algorithm.
 *
 * This is a bi-directional variant of Bubble Sort that traverses the list
 * alternately in both directions, bubbling the largest element to the end
 * and the smallest element to the beginning in each full pass.
 */
class CocktailShakerSort implements SortAlgorithm {

    /**
     * Sorts the given array using Cocktail Shaker Sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            end = forwardPass(array, start, end);
            start = backwardPass(array, start, end);
        }

        return array;
    }

    /**
     * Performs a forward pass from left to right, bubbling the largest element
     * towards the end of the array segment.
     *
     * @param array the array being sorted
     * @param start the starting index (inclusive)
     * @param end   the ending index (inclusive)
     * @param <T>   the type of elements, which must be comparable
     * @return the last index where a swap occurred
     */
    private <T extends Comparable<T>> int forwardPass(final T[] array, final int start, final int end) {
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
     * Performs a backward pass from right to left, bubbling the smallest element
     * towards the beginning of the array segment.
     *
     * @param array the array being sorted
     * @param start the starting index (inclusive)
     * @param end   the ending index (inclusive)
     * @param <T>   the type of elements, which must be comparable
     * @return the last index where a swap occurred
     */
    private <T extends Comparable<T>> int backwardPass(final T[] array, final int start, final int end) {
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