package com.thealgorithms.sorts;

public class FlashSort implements SortAlgorithm {

    private static final String RATIO_ERROR_MESSAGE =
            "Classification ratio must be between 0 and 1 (exclusive).";

    private static final double DEFAULT_CLASSIFICATION_RATIO = 0.45;

    private double classificationRatio = DEFAULT_CLASSIFICATION_RATIO;

    public FlashSort() {}

    public FlashSort(double classificationRatio) {
        setClassificationRatio(classificationRatio);
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
            throw new IllegalArgumentException(RATIO_ERROR_MESSAGE);
        }
    }

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        flashSort(array);
        return array;
    }

    private <T extends Comparable<? super T>> void flashSort(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        final T min = findMin(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(min) == 0) {
            return;
        }

        final int bucketCount = (int) (classificationRatio * array.length);
        final int[] bucketSizes = new int[bucketCount];

        final double c1 = (double) (bucketCount - 1) / array[maxIndex].compareTo(min);

        classify(array, bucketSizes, c1, min);
        accumulate(bucketSizes);
        permute(array, bucketSizes, c1, min);
        insertionSort(array);
    }

    private <T extends Comparable<? super T>> T findMin(final T[] array) {
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    private <T extends Comparable<? super T>> int findMaxIndex(final T[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private <T extends Comparable<? super T>> void classify(
            final T[] array,
            final int[] bucketSizes,
            final double c1,
            final T min
    ) {
        for (T value : array) {
            int bucketIndex = (int) (c1 * value.compareTo(min));
            bucketSizes[bucketIndex]++;
        }
    }

    private void accumulate(final int[] bucketSizes) {
        for (int i = 1; i < bucketSizes.length; i++) {
            bucketSizes[i] += bucketSizes[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void permute(
            final T[] array,
            final int[] bucketEnds,
            final double c1,
            final T min
    ) {
        final int n = array.length;
        final int lastBucketIndex = bucketEnds.length - 1;

        int moved = 0;
        int currentIndex = 0;
        int currentBucket = lastBucketIndex;

        while (moved < n - 1) {
            while (currentIndex > bucketEnds[currentBucket] - 1) {
                currentIndex++;
                currentBucket = (int) (c1 * array[currentIndex].compareTo(min));
            }

            T flash = array[currentIndex];

            while (currentIndex != bucketEnds[currentBucket]) {
                currentBucket = (int) (c1 * flash.compareTo(min));
                int targetIndex = bucketEnds[currentBucket] - 1;

                T temp = array[targetIndex];
                array[targetIndex] = flash;
                flash = temp;

                bucketEnds[currentBucket]--;
                moved++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        for (int i = 1; i < array.length; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }
}