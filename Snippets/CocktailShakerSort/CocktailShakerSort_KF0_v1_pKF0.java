package com.thealgorithms.sorts;

/**
 * CocktailShakerSort implements a bidirectional bubble sort.
 * It repeatedly passes through the array in both directions,
 * moving larger elements to the end and smaller elements to the beginning.
 */
class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array == null || array.length < 2) {
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
     * Forward pass: bubbles the largest element in the current range to the right.
     */
    private <T extends Comparable<T>> int forwardPass(final T[] array, final int left, final int right) {
        int lastSwapPosition = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapPosition = i;
            }
        }

        return lastSwapPosition;
    }

    /**
     * Backward pass: bubbles the smallest element in the current range to the left.
     */
    private <T extends Comparable<T>> int backwardPass(final T[] array, final int left, final int right) {
        int lastSwapPosition = right;

        for (int i = right; i > left; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwapPosition = i;
            }
        }

        return lastSwapPosition;
    }
}