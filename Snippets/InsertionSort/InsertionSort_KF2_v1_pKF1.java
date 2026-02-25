package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int startIndex, final int endIndex) {
        if (array == null || startIndex >= endIndex) {
            return array;
        }

        for (int currentIndex = startIndex + 1; currentIndex < endIndex; currentIndex++) {
            final T key = array[currentIndex];
            int compareIndex = currentIndex - 1;

            while (compareIndex >= startIndex && SortUtils.less(key, array[compareIndex])) {
                array[compareIndex + 1] = array[compareIndex];
                compareIndex--;
            }

            array[compareIndex + 1] = key;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minElementIndex = findMinIndex(array);
        SortUtils.swap(array, 0, minElementIndex);

        for (int currentIndex = 2; currentIndex < array.length; currentIndex++) {
            final T currentValue = array[currentIndex];
            int compareIndex = currentIndex;

            while (compareIndex > 0 && SortUtils.less(currentValue, array[compareIndex - 1])) {
                array[compareIndex] = array[compareIndex - 1];
                compareIndex--;
            }

            array[compareIndex] = currentValue;
        }

        return array;
    }

    private <T extends Comparable<T>> int findMinIndex(final T[] array) {
        int minIndex = 0;

        for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
            if (SortUtils.less(array[currentIndex], array[minIndex])) {
                minIndex = currentIndex;
            }
        }

        return minIndex;
    }
}