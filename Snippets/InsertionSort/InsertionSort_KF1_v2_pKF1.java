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
            final T currentValue = array[currentIndex];
            int sortedIndex = currentIndex - 1;

            while (sortedIndex >= startIndex && SortUtils.less(currentValue, array[sortedIndex])) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = currentValue;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sortWithSentinel(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minimumIndex = findIndexOfMinimum(array);
        SortUtils.swap(array, 0, minimumIndex);

        for (int currentIndex = 2; currentIndex < array.length; currentIndex++) {
            final T currentValue = array[currentIndex];
            int sortedIndex = currentIndex;

            while (sortedIndex > 0 && SortUtils.less(currentValue, array[sortedIndex - 1])) {
                array[sortedIndex] = array[sortedIndex - 1];
                sortedIndex--;
            }
            array[sortedIndex] = currentValue;
        }

        return array;
    }

    private <T extends Comparable<T>> int findIndexOfMinimum(final T[] array) {
        int minimumIndex = 0;
        for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
            if (SortUtils.less(array[currentIndex], array[minimumIndex])) {
                minimumIndex = currentIndex;
            }
        }
        return minimumIndex;
    }
}