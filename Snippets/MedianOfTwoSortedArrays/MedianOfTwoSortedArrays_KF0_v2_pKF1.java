package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    /**
     * Finds the median of two sorted arrays in logarithmic time.
     *
     * @param smallerArray the first sorted array
     * @param largerArray  the second sorted array
     * @return the median of the combined sorted array
     * @throws IllegalArgumentException if the input arrays are not sorted
     */
    public static double findMedianSortedArrays(int[] smallerArray, int[] largerArray) {
        if (smallerArray.length > largerArray.length) {
            return findMedianSortedArrays(largerArray, smallerArray); // Ensure smallerArray is the smaller array
        }

        int smallerLength = smallerArray.length;
        int largerLength = largerArray.length;
        int leftIndex = 0;
        int rightIndex = smallerLength;

        while (leftIndex <= rightIndex) {
            int partitionSmaller = (leftIndex + rightIndex) / 2;
            int partitionLarger = (smallerLength + largerLength + 1) / 2 - partitionSmaller;

            int maxLeftSmaller =
                (partitionSmaller == 0) ? Integer.MIN_VALUE : smallerArray[partitionSmaller - 1];
            int minRightSmaller =
                (partitionSmaller == smallerLength) ? Integer.MAX_VALUE : smallerArray[partitionSmaller];

            int maxLeftLarger =
                (partitionLarger == 0) ? Integer.MIN_VALUE : largerArray[partitionLarger - 1];
            int minRightLarger =
                (partitionLarger == largerLength) ? Integer.MAX_VALUE : largerArray[partitionLarger];

            boolean isValidPartition =
                maxLeftSmaller <= minRightLarger && maxLeftLarger <= minRightSmaller;

            if (isValidPartition) {
                boolean isTotalLengthOdd = ((smallerLength + largerLength) & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeftSmaller, maxLeftLarger);
                } else {
                    int maxOfLeft = Math.max(maxLeftSmaller, maxLeftLarger);
                    int minOfRight = Math.min(minRightSmaller, minRightLarger);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            }

            if (maxLeftSmaller > minRightLarger) {
                rightIndex = partitionSmaller - 1;
            } else {
                leftIndex = partitionSmaller + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}