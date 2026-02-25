package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    /**
     * Finds the median of two sorted integer arrays.
     *
     * @param firstArray  first sorted array
     * @param secondArray second sorted array
     * @return median value as a double
     * @throws IllegalArgumentException if input arrays are not sorted in non-decreasing order
     */
    public static double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
        if (firstArray.length > secondArray.length) {
            return findMedianSortedArrays(secondArray, firstArray);
        }

        int lengthFirstArray = firstArray.length;
        int lengthSecondArray = secondArray.length;
        int leftIndex = 0;
        int rightIndex = lengthFirstArray;

        while (leftIndex <= rightIndex) {
            int partitionFirstArray = (leftIndex + rightIndex) / 2;
            int partitionSecondArray =
                (lengthFirstArray + lengthSecondArray + 1) / 2 - partitionFirstArray;

            int maxLeftFirstArray =
                (partitionFirstArray == 0) ? Integer.MIN_VALUE : firstArray[partitionFirstArray - 1];
            int minRightFirstArray =
                (partitionFirstArray == lengthFirstArray)
                    ? Integer.MAX_VALUE
                    : firstArray[partitionFirstArray];

            int maxLeftSecondArray =
                (partitionSecondArray == 0)
                    ? Integer.MIN_VALUE
                    : secondArray[partitionSecondArray - 1];
            int minRightSecondArray =
                (partitionSecondArray == lengthSecondArray)
                    ? Integer.MAX_VALUE
                    : secondArray[partitionSecondArray];

            boolean isValidPartition =
                maxLeftFirstArray <= minRightSecondArray && maxLeftSecondArray <= minRightFirstArray;

            if (isValidPartition) {
                boolean isTotalLengthOdd =
                    ((lengthFirstArray + lengthSecondArray) & 1) == 1;
                if (isTotalLengthOdd) {
                    return Math.max(maxLeftFirstArray, maxLeftSecondArray);
                } else {
                    int maxOfLeft = Math.max(maxLeftFirstArray, maxLeftSecondArray);
                    int minOfRight = Math.min(minRightFirstArray, minRightSecondArray);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            } else if (maxLeftFirstArray > minRightSecondArray) {
                rightIndex = partitionFirstArray - 1;
            } else {
                leftIndex = partitionFirstArray + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}