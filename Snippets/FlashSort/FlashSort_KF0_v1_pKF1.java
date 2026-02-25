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
    private double classificationRatio = 0.45;

    public FlashSort() {
    }

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

    /**
     * Sorts an array using the Flash Sort algorithm.
     *
     * @param array the array to be sorted.
     * @param <T> the type of elements to be sorted, must be comparable.
     * @return the sorted array.
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        flashSort(array);
        return array;
    }

    /**
     * Sorts an array using the Flash Sort algorithm.
     *
     * @param array the array to be sorted.
     * @param <T> the type of elements to be sorted, must be comparable.
     */
    private <T extends Comparable<? super T>> void flashSort(T[] array) {
        int length = array.length;
        if (length == 0) {
            return;
        }

        final T minValue = findMin(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(minValue) == 0) {
            return; // All elements are the same
        }

        final int numberOfClasses = (int) (classificationRatio * length);
        final int[] classCounts = new int[numberOfClasses];

        final double normalizationFactor = (double) (numberOfClasses - 1) / array[maxIndex].compareTo(minValue);

        classify(array, classCounts, normalizationFactor, minValue);
        accumulateClassCounts(classCounts);
        permute(array, classCounts, normalizationFactor, minValue, length, numberOfClasses);
        insertionSort(array);
    }

    /**
     * Finds the minimum value in the array.
     *
     * @param array the array to find the minimum value in.
     * @param <T> the type of elements in the array, must be comparable.
     * @return the minimum value in the array.
     */
    private <T extends Comparable<? super T>> T findMin(final T[] array) {
        T minValue = array[0];
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(minValue) < 0) {
                minValue = array[index];
            }
        }
        return minValue;
    }

    /**
     * Finds the index of the maximum value in the array.
     *
     * @param array the array to find the maximum value index in.
     * @param <T> the type of elements in the array, must be comparable.
     * @return the index of the maximum value in the array.
     */
    private <T extends Comparable<? super T>> int findMaxIndex(final T[] array) {
        int maxIndex = 0;
        for (int index = 1; index < array.length; index++) {
            if (array[index].compareTo(array[maxIndex]) > 0) {
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    /**
     * Classifies elements of the array into the classCounts array.
     *
     * @param array the array to be classified.
     * @param classCounts the classification array holding the count of elements in each class.
     * @param normalizationFactor the normalization constant used to map the elements to the classification array.
     * @param minValue the minimum value in the array.
     * @param <T> the type of elements in the array, must be comparable.
     */
    private <T extends Comparable<? super T>> void classify(
        final T[] array,
        final int[] classCounts,
        final double normalizationFactor,
        final T minValue
    ) {
        for (T element : array) {
            int classIndex = (int) (normalizationFactor * element.compareTo(minValue));
            classCounts[classIndex]++;
        }
    }

    /**
     * Transforms the classCounts array into a prefix sum array of starting indices.
     *
     * @param classCounts the classification array holding the count of elements in each class.
     */
    private void accumulateClassCounts(final int[] classCounts) {
        for (int index = 1; index < classCounts.length; index++) {
            classCounts[index] += classCounts[index - 1];
        }
    }

    /**
     * Permutes the array into sorted order based on the classCounts array.
     *
     * @param array the array to be permuted.
     * @param classCounts the classification array holding the count of elements in each class.
     * @param normalizationFactor the normalization constant used to map the elements to the classification array.
     * @param minValue the minimum value in the array.
     * @param length the length of the array.
     * @param numberOfClasses the number of classes in the classification array.
     * @param <T> the type of elements in the array, must be comparable.
     */
    private <T extends Comparable<? super T>> void permute(
        final T[] array,
        final int[] classCounts,
        final double normalizationFactor,
        final T minValue,
        final int length,
        final int numberOfClasses
    ) {
        int movedElements = 0;
        int currentIndex = 0;
        int currentClassIndex = numberOfClasses - 1;
        T currentElement;

        while (movedElements < length - 1) {
            while (currentIndex > classCounts[currentClassIndex] - 1) {
                currentIndex++;
                currentClassIndex = (int) (normalizationFactor * array[currentIndex].compareTo(minValue));
            }
            currentElement = array[currentIndex];
            while (currentIndex != classCounts[currentClassIndex]) {
                currentClassIndex = (int) (normalizationFactor * currentElement.compareTo(minValue));
                int targetIndex = classCounts[currentClassIndex] - 1;
                T temp = array[targetIndex];
                array[targetIndex] = currentElement;
                currentElement = temp;
                classCounts[currentClassIndex]--;
                movedElements++;
            }
        }
    }

    /**
     * Sorts an array using the insertion sort algorithm.
     *
     * @param array the array to be sorted.
     * @param <T> the type of elements to be sorted, must be comparable.
     */
    private <T extends Comparable<? super T>> void insertionSort(final T[] array) {
        int length = array.length;
        for (int index = 1; index < length; index++) {
            T key = array[index];
            int previousIndex = index - 1;
            while (previousIndex >= 0 && array[previousIndex].compareTo(key) > 0) {
                array[previousIndex + 1] = array[previousIndex];
                previousIndex--;
            }
            array[previousIndex + 1] = key;
        }
    }
}