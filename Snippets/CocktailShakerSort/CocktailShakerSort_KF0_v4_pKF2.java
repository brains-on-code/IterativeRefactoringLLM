package com.thealgorithms.sorts;

/**
 * Cocktail Shaker Sort (bidirectional bubble sort).
 *
 * Repeatedly traverses the array in both directions:
 * - Forward pass: moves larger elements toward the end.
 * - Backward pass: moves smaller elements toward the beginning.
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

        int leftBoundary = 0;
        int rightBoundary = array.length - 1;

        while (leftBoundary < rightBoundary) {
            rightBoundary = forwardPass(array, leftBoundary, rightBoundary);
            leftBoundary = backwardPass(array, leftBoundary, rightBoundary);
        }

        return array;
    }

    /**
     * Performs a forward pass from {@code leftBoundary} to {@code rightBoundary},
     * moving larger elements toward the right.
     *
     * @param array         array to sort
     * @param leftBoundary  inclusive left boundary of this pass
     * @param rightBoundary inclusive right boundary of this pass
     * @param <T>           comparable element type
     * @return index of the last swap (new right boundary)
     */
    private <T extends Comparable<T>> int forwardPass(
        final T[] array,
        final int leftBoundary,
        final int rightBoundary
    ) {
        int lastSwapIndex = leftBoundary;

        for (int i = leftBoundary; i < rightBoundary; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }

    /**
     * Performs a backward pass from {@code rightBoundary} to {@code leftBoundary},
     * moving smaller elements toward the left.
     *
     * @param array         array to sort
     * @param leftBoundary  inclusive left boundary of this pass
     * @param rightBoundary inclusive right boundary of this pass
     * @param <T>           comparable element type
     * @return index of the last swap (new left boundary)
     */
    private <T extends Comparable<T>> int backwardPass(
        final T[] array,
        final int leftBoundary,
        final int rightBoundary
    ) {
        int lastSwapIndex = rightBoundary;

        for (int i = rightBoundary; i > leftBoundary; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }
}