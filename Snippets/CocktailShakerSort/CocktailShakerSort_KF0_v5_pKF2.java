package com.thealgorithms.sorts;

/**
 * Cocktail Shaker Sort (bidirectional bubble sort).
 *
 * Traverses the array alternately from left to right and right to left:
 * - Forward pass: pushes larger elements toward the end.
 * - Backward pass: pushes smaller elements toward the beginning.
 *
 * The effective sorting range shrinks each iteration based on the last swap
 * positions in each pass.
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
     * Forward pass from {@code left} to {@code right}, moving larger elements rightward.
     *
     * @param array array to sort
     * @param left  inclusive left boundary of this pass
     * @param right inclusive right boundary of this pass
     * @param <T>   comparable element type
     * @return index of the last swap (new right boundary)
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
     * Backward pass from {@code right} to {@code left}, moving smaller elements leftward.
     *
     * @param array array to sort
     * @param left  inclusive left boundary of this pass
     * @param right inclusive right boundary of this pass
     * @param <T>   comparable element type
     * @return index of the last swap (new left boundary)
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