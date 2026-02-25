package com.thealgorithms.sorts;

/**
 * Cocktail Shaker Sort implementation.
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
            rightBoundary = performForwardPass(array, leftBoundary, rightBoundary);
            leftBoundary = performBackwardPass(array, leftBoundary, rightBoundary);
        }

        return array;
    }

    private <T extends Comparable<T>> int performForwardPass(final T[] array, final int start, final int end) {
        int lastSwapPosition = start;

        for (int currentIndex = start; currentIndex < end; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }

    private <T extends Comparable<T>> int performBackwardPass(final T[] array, final int start, final int end) {
        int lastSwapPosition = end;

        for (int currentIndex = end; currentIndex > start; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }
}