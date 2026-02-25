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
            rightBoundary = performForwardPass(array, leftBoundary, rightBoundary);
            leftBoundary = performBackwardPass(array, leftBoundary, rightBoundary);
        }

        return array;
    }

    /**
     * Forward pass: bubbles the largest element in the current range to the right.
     */
    private <T extends Comparable<T>> int performForwardPass(final T[] array, final int left, final int right) {
        int lastSwapPosition = left;

        for (int currentIndex = left; currentIndex < right; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }

    /**
     * Backward pass: bubbles the smallest element in the current range to the left.
     */
    private <T extends Comparable<T>> int performBackwardPass(final T[] array, final int left, final int right) {
        int lastSwapPosition = right;

        for (int currentIndex = right; currentIndex > left; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }
}