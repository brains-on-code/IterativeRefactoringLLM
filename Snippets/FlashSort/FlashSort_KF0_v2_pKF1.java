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
    private double classRatio = 0.45;

    public FlashSort() {}

    public FlashSort(double classRatio) {
        validateClassRatio(classRatio);
        this.classRatio = classRatio;
    }

    public double getClassRatio() {
        return classRatio;
    }

    public void setClassRatio(double classRatio) {
        validateClassRatio(classRatio);
        this.classRatio = classRatio;
    }

    private void validateClassRatio(double ratio) {
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

        final T minElement = findMin(array);
        final int maxIndex = findMaxIndex(array);

        if (array[maxIndex].compareTo(minElement) == 0) {
            return; // All elements are the same
        }

        final int classCount = (int) (classRatio * length);
        final int[] classBoundaries = new int[classCount];

        final double normalizationFactor =
            (double) (classCount - 1) / array[maxIndex].compareTo(minElement);

        classify(array, classBoundaries, normalizationFactor, minElement);
        accumulateClassCounts(classBoundaries);
        permute(array, classBoundaries, normalizationFactor, minElement, length, classCount);
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
        T minElement = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(minElement) < 0) {
                minElement = array[i];
            }
        }
        return minElement;
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
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Classifies elements of the array into the classBoundaries array.
     *
     * @param array the array to be classified.
     * @param classBoundaries the classification array holding the count of elements in each class.
     * @param normalizationFactor the normalization constant used to map the elements to the classification array.
     * @param minElement the minimum value in the array.
     * @param <T> the type of elements in the array, must be comparable.
     */
    private <T extends Comparable<? super T>> void classify(
        final T[] array,
        final int[] classBoundaries,
        final double normalizationFactor,
        final T minElement
    ) {
        for (T element : array) {
            int classIndex = (int) (normalizationFactor * element.compareTo(minElement));
            classBoundaries[classIndex]++;
        }
    }

    /**
     * Transforms the classBoundaries array into a prefix sum array of starting indices.
     *
     * @param classBoundaries the classification array holding the count of elements in each class.
     */
    private void accumulateClassCounts(final int[] classBoundaries) {
        for (int i = 1; i < classBoundaries.length; i++) {
            classBoundaries[i] += classBoundaries[i - 1];
        }
    }

    /**
     * Permutes the array into sorted order based on the classBoundaries array.
     *
     * @param array the array to be permuted.
     * @param classBoundaries the classification array holding the count of elements in each class.
     * @param normalizationFactor the normalization constant used to map the elements to the classification array.
     * @param minElement the minimum value in the array.
     * @param length the length of the array.
     * @param classCount the number of classes in the classification array.
     * @param <T> the type of elements in the array, must be comparable.
     */
    private <T extends Comparable<? super T>> void permute(
        final T[] array,
        final int[] classBoundaries,
        final double normalizationFactor,
        final T minElement,
        final int length,
        final int classCount
    ) {
        int movedElementCount = 0;
        int currentIndex = 0;
        int currentClassIndex = classCount - 1;
        T currentElement;

        while (movedElementCount < length - 1) {
            while (currentIndex > classBoundaries[currentClassIndex] - 1) {
                currentIndex++;
                currentClassIndex =
                    (int) (normalizationFactor * array[currentIndex].compareTo(minElement));
            }
            currentElement = array[currentIndex];
            while (currentIndex != classBoundaries[currentClassIndex]) {
                currentClassIndex =
                    (int) (normalizationFactor * currentElement.compareTo(minElement));
                int targetIndex = classBoundaries[currentClassIndex] - 1;
                T temp = array[targetIndex];
                array[targetIndex] = currentElement;
                currentElement = temp;
                classBoundaries[currentClassIndex]--;
                movedElementCount++;
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
        for (int i = 1; i < length; i++) {
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