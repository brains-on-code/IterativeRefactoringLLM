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
        if (array == null || array.length <= 1) {
            return array;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            right = forwardPass(array, left, right);
            if (left >= right) {
                break;
            }

            left = backwardPass(array, left, right);
        }

        return array;
    }

    /**
     * Performs a forward pass from left to right, bubbling the largest element
     * towards the end of the array segment.
     *
     * @param array the array being sorted
     * @param left  the starting index (inclusive)
     * @param right the ending index (inclusive)
     * @param <T>   the type of elements, which must be comparable
     * @return the last index where a swap occurred
     */
    private <T extends Comparable<T>> int forwardPass(
        final T[] array,
        final int left,
        final int right
    ) {
        int lastSwapIndex = left;

        for (int i = left; i < right; i++) {
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
     * @param left  the starting index (inclusive)
     * @param right the ending index (inclusive)
     * @param <T>   the type of elements, which must be comparable
     * @return the last index where a swap occurred
     */
    private <T extends Comparable<T>> int backwardPass(
        final T[] array,
        final int left,
        final int right
    ) {
        int lastSwapIndex = right;

        for (int i = right; i > left; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }
}