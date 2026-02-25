package com.thealgorithms.sorts;

class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array == null || array.length <= 1) {
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

    private <T extends Comparable<T>> int performForwardPass(final T[] array, final int leftBoundary, final int rightBoundary) {
        int lastSwappedIndex = leftBoundary;

        for (int currentIndex = leftBoundary; currentIndex < rightBoundary; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwappedIndex = currentIndex;
            }
        }

        return lastSwappedIndex;
    }

    private <T extends Comparable<T>> int performBackwardPass(final T[] array, final int leftBoundary, final int rightBoundary) {
        int lastSwappedIndex = rightBoundary;

        for (int currentIndex = rightBoundary; currentIndex > leftBoundary; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwappedIndex = currentIndex;
            }
        }

        return lastSwappedIndex;
    }
}