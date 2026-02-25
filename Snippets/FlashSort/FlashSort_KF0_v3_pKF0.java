package com.thealgorithms.sorts;

/**
 * Implementation of Flash Sort algorithm that implements the SortAlgorithm interface.
 *
 * Sorts an array using the Flash Sort algorithm.
 * <p>
 * Flash Sort is a distribution sorting algorithm that partitions the data into
 * different classes based on a classification array. It performs the sorting by
 * first distributing the data elements into different buckets (or classes) and
 * then permuting these buckets into the sorted order.
 * <p>
 * The method works as follows:
 * <ol>
 *     <li>Finds the minimum and maximum values in the array.</li>
 *     <li>Initializes a classification array `L` to keep track of the number of elements in each class.</li>
 *     <li>Computes a normalization constant `c1` to map elements into classes.</li>
 *     <li>Classifies each element of the array into the corresponding bucket in the classification array.</li>
 *     <li>Transforms the classification array to compute the starting indices of each bucket.</li>
 *     <li>Permutes the elements of the array into sorted order based on the classification.</li>
 *     <li>Uses insertion sort for the final arrangement to ensure complete sorting.</li>
 * </ol>
 */
public class FlashSort implements SortAlgorithm {

    private static final String RATIO_ERROR_MESSAGE =
        "Classification ratio must be between 0 and 1 (exclusive).";

    private static final double DEFAULT_CLASSIFICATION_RATIO = 0.45;

    private double classificationRatio;

    public FlashSort() {
        this(DEFAULT_CLASSIFICATION_RATIO);
    }

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

    private <T extends Comparable<? super T>> void flashSort(T[] arr) {
        int length = arr.length;
        if (length == 0) {
            return;
        }

        final T min = findMin(arr);
        final int maxIndex = findMaxIndex(arr);

        if (arr[maxIndex].compareTo(min) == 0) {
            return;
        }

        final int classCount = (int) (classificationRatio * length);
        final int[] classCounts = new int[classCount];

        final double normalizationConstant = (double) (classCount - 1) / arr[maxIndex].compareTo(min);

        classify(arr, classCounts, normalizationConstant, min);
        accumulateClassCounts(classCounts);
        permute(arr, classCounts, normalizationConstant, min, length, classCount);
        insertionSort(arr);
    }

    private <T extends Comparable<? super T>> T findMin(final T[] arr) {
        T min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(min) < 0) {
                min = arr[i];
            }
        }
        return min;
    }

    private <T extends Comparable<? super T>> int findMaxIndex(final T[] arr) {
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private <T extends Comparable<? super T>> void classify(
        final T[] arr,
        final int[] classCounts,
        final double normalizationConstant,
        final T min
    ) {
        for (T value : arr) {
            int classIndex = (int) (normalizationConstant * value.compareTo(min));
            classCounts[classIndex]++;
        }
    }

    private void accumulateClassCounts(final int[] classCounts) {
        for (int i = 1; i < classCounts.length; i++) {
            classCounts[i] += classCounts[i - 1];
        }
    }

    private <T extends Comparable<? super T>> void permute(
        final T[] arr,
        final int[] classCounts,
        final double normalizationConstant,
        final T min,
        final int length,
        final int classCount
    ) {
        int movedElements = 0;
        int currentIndex = 0;
        int currentClass = classCount - 1;

        while (movedElements < length - 1) {
            while (currentIndex > classCounts[currentClass] - 1) {
                currentIndex++;
                currentClass = (int) (normalizationConstant * arr[currentIndex].compareTo(min));
            }

            T flash = arr[currentIndex];

            while (currentIndex != classCounts[currentClass]) {
                currentClass = (int) (normalizationConstant * flash.compareTo(min));
                int targetIndex = classCounts[currentClass] - 1;

                T temp = arr[targetIndex];
                arr[targetIndex] = flash;
                flash = temp;

                classCounts[currentClass]--;
                movedElements++;
            }
        }
    }

    private <T extends Comparable<? super T>> void insertionSort(final T[] arr) {
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            T key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}