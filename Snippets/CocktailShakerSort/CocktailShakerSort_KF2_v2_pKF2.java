package com.thealgorithms.sorts;

class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int leftBoundary = 0;
        int rightBoundary = array.length - 1;

        while (leftBoundary < rightBoundary) {
            rightBoundary = moveLargerElementsRight(array, leftBoundary, rightBoundary);
            leftBoundary = moveSmallerElementsLeft(array, leftBoundary, rightBoundary);
        }

        return array;
    }

    private <T extends Comparable<T>> int moveLargerElementsRight(final T[] array, final int left, final int right) {
        int lastSwapPosition = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapPosition = i;
            }
        }

        return lastSwapPosition;
    }

    private <T extends Comparable<T>> int moveSmallerElementsLeft(final T[] array, final int left, final int right) {
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