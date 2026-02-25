package com.thealgorithms.sorts;

public class FlashSort implements SortAlgorithm {
    private double classificationRatio = 0.45;

    public FlashSort() {}

    public FlashSort(double classificationRatio) {
        validateClassificationRatio(classificationRatio);
        this.classificationRatio = classificationRatio;
    }

    public double getClassificationRatio() {
        return classificationRatio;
    }

    public void setClassificationRatio(double classificationRatio) {
        validateClassificationRatio(classificationRatio);
        this.classificationRatio = classificationRatio;
    }

    private void validateClassificationRatio(double ratio) {
        if (ratio <= 0 || ratio >= 1) {
            throw new IllegalArgumentException("Classification ratio must be between 0 and 1 (exclusive).");
        }
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        flashSort(array);
        return array;
    }

    private <T extends Comparable<? super T>> void flashSort(T[] array) {
        if (array.length == 0) {
            return;
        }

        final T minValue = findMinValue(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(minValue) == 0) {
            return;
        }

        final int bucketCount = (int) (classificationRatio * array.length);
        final int[] bucketCounts = new int[bucketCount];

        final double bucketScale = (double) (bucketCount - 1) / array[maxIndex].compareTo(minValue);

        classifyElements(array, bucketCounts, bucketScale, minValue);
        accumulateBucketCounts(bucketCounts);
        permuteElements(array, bucketCounts, bucketScale, minValue, array.length, bucketCount);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMinValue(final T[] array) {
        T minValue = array[0];
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(minValue) < 0) {
                minValue = array[index];
            }
        }
        return minValue;
    }

    private <T extends Comparable<? super T>> int findMaxIndex(final T[] array) {
        int maxIndex = 0;
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(array[maxIndex]) > 0) {
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    private <T extends Comparable<? super T>> void classifyElements(
            final T[] array,
            final int[] bucketCounts,
            final double bucketScale,
            final T minValue
    ) {
        for (T element : array) {
            int bucketIndex = (int) (bucketScale * element.compareTo(minValue));
            bucketCounts[bucketIndex]++;
        }
    }

    private void accumulateBucketCounts(final int[] bucketCounts) {
        for (int index = 1; index < bucketCounts.length; index++) {
            bucketCounts[index] += bucketCounts[index - 1];
        }
    }

    private <T extends Comparable<? super T>> void permuteElements(
            final T[] array,
            final int[] bucketCounts,
            final double bucketScale,
            final T minValue,
            int arrayLength,
            int bucketCount
    ) {
        int movedElements = 0;
        int currentIndex = 0;
        int currentBucketIndex = bucketCount - 1;
        T currentElement;

        while (movedElements < arrayLength - 1) {
            while (currentIndex > bucketCounts[currentBucketIndex] - 1) {
                currentIndex++;
                currentBucketIndex = (int) (bucketScale * array[currentIndex].compareTo(minValue));
            }
            currentElement = array[currentIndex];
            while (currentIndex != bucketCounts[currentBucketIndex]) {
                currentBucketIndex = (int) (bucketScale * currentElement.compareTo(minValue));
                int targetIndex = bucketCounts[currentBucketIndex] - 1;
                T temp = array[targetIndex];
                array[targetIndex] = currentElement;
                currentElement = temp;
                bucketCounts[currentBucketIndex]--;
                movedElements++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int length = array.length;
        for (int index = 1; index < length; index++) {
            T key = array[index];
            int sortedIndex = index - 1;
            while (sortedIndex >= 0 && array[sortedIndex].compareTo(key) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = key;
        }
    }
}