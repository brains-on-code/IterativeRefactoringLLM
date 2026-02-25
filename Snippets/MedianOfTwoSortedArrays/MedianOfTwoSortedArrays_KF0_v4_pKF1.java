package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    /**
     * Finds the median of two sorted arrays in logarithmic time.
     *
     * @param firstArray  the first sorted array
     * @param secondArray the second sorted array
     * @return the median of the combined sorted array
     * @throws IllegalArgumentException if the input arrays are not sorted
     */
    public static double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
        if (firstArray.length > secondArray.length) {
            return findMedianSortedArrays(secondArray, firstArray); // Ensure firstArray is the smaller array
        }

        int firstArrayLength = firstArray.length;
        int secondArrayLength = secondArray.length;
        int left = 0;
        int right = firstArrayLength;

        while (left <= right) {
            int partitionFirstArray = (left + right) / 2;
            int partitionSecondArray =
                (firstArrayLength + secondArrayLength + 1) / 2 - partitionFirstArray;

            int maxLeftFirstArray =
                (partitionFirstArray == 0) ? Integer.MIN_VALUE : firstArray[partitionFirstArray - 1];
            int minRightFirstArray =
                (partitionFirstArray == firstArrayLength) ? Integer.MAX_VALUE : firstArray[partitionFirstArray];

            int maxLeftSecondArray =
                (partitionSecondArray == 0) ? Integer.MIN_VALUE : secondArray[partitionSecondArray - 1];
            int minRightSecondArray =
                (partitionSecondArray == secondArrayLength) ? Integer.MAX_VALUE : secondArray[partitionSecondArray];

            boolean isValidPartition =
                maxLeftFirstArray <= minRightSecondArray && maxLeftSecondArray <= minRightFirstArray;

            if (isValidPartition) {
                boolean isTotalLengthOdd = ((firstArrayLength + secondArrayLength) & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeftFirstArray, maxLeftSecondArray);
                } else {
                    int maxOfLeft = Math.max(maxLeftFirstArray, maxLeftSecondArray);
                    int minOfRight = Math.min(minRightFirstArray, minRightSecondArray);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            }

            if (maxLeftFirstArray > minRightSecondArray) {
                right = partitionFirstArray - 1;
            } else {
                left = partitionFirstArray + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}