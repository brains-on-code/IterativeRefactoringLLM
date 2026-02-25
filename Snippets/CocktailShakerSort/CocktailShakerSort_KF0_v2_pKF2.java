package com.thealgorithms.sorts;

/**
 * Implements Cocktail Shaker Sort (also known as bidirectional bubble sort).
 *
 * The algorithm repeatedly traverses the array in both directions:
 * - A forward pass moves larger elements toward the end.
 * - A backward pass moves smaller elements toward the beginning.
 *
 * The effective sorting range is narrowed each iteration using the positions
 * of the last swaps in each pass.
 */
class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            right = forwardPass(array, left, right);
            left = backwardPass(array, left, right);
        }

        return array;
    }

    /**
     * Performs a forward pass from {@code left} to {@code right}, bubbling
     * larger elements toward the right side of the array.
     *
     * @param array the array to sort
     * @param left  the left boundary (inclusive) of the current pass
     * @param right the right boundary (inclusive) of the current pass
     * @param <T>   the element type, which must be comparable
     * @return the index of the last swap, used as the new right boundary
     */
    private <T extends Comparable<T>> int forwardPass(
        final T[] array,
        final int left,
        final int right
    ) {
        int lastSwap = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwap = i;
            }
        }

        return lastSwap;
    }

    /**
     * Performs a backward pass from {@code right} to {@code left}, bubbling
     * smaller elements toward the left side of the array.
     *
     * @param array the array to sort
     * @param left  the left boundary (inclusive) of the current pass
     * @param right the right boundary (inclusive) of the current pass
     * @param <T>   the element type, which must be comparable
     * @return the index of the last swap, used as the new left boundary
     */
    private <T extends Comparable<T>> int backwardPass(
        final T[] array,
        final int left,
        final int right
    ) {
        int lastSwap = right;

        for (int i = right; i > left; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwap = i;
            }
        }

        return lastSwap;
    }
}