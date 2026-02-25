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

        int leftIndex = 0;
        int rightIndex = array.length - 1;

        while (leftIndex < rightIndex) {
            rightIndex = forwardPass(array, leftIndex, rightIndex);
            leftIndex = backwardPass(array, leftIndex, rightIndex);
        }

        return array;
    }

    private <T extends Comparable<T>> int forwardPass(final T[] array, final int startIndex, final int endIndex) {
        int lastSwapIndex = startIndex;

        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwapIndex = currentIndex;
            }
        }

        return lastSwapIndex;
    }

    private <T extends Comparable<T>> int backwardPass(final T[] array, final int startIndex, final int endIndex) {
        int lastSwapIndex = endIndex;

        for (int currentIndex = endIndex; currentIndex > startIndex; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwapIndex = currentIndex;
            }
        }

        return lastSwapIndex;
    }
}