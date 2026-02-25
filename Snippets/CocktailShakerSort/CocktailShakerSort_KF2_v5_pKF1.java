package com.thealgorithms.sorts;

class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int startIndex = 0;
        int endIndex = array.length - 1;

        while (startIndex < endIndex) {
            endIndex = forwardPass(array, startIndex, endIndex);
            startIndex = backwardPass(array, startIndex, endIndex);
        }

        return array;
    }

    private <T extends Comparable<T>> int forwardPass(final T[] array, final int startIndex, final int endIndex) {
        int lastSwapPosition = startIndex;

        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }

    private <T extends Comparable<T>> int backwardPass(final T[] array, final int startIndex, final int endIndex) {
        int lastSwapPosition = endIndex;

        for (int currentIndex = endIndex; currentIndex > startIndex; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }
}