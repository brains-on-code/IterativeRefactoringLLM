package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the median of two sorted arrays in logarithmic time.
     *
     * @param nums1 the first sorted array
     * @param nums2 the second sorted array
     * @return the median of the combined sorted array
     * @throws IllegalArgumentException if the input arrays are not sorted
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to keep the binary search space minimal
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int length1 = nums1.length;
        int length2 = nums2.length;
        int totalLength = length1 + length2;

        int low = 0;
        int high = length1;

        while (low <= high) {
            int partition1 = (low + high) / 2;
            int partition2 = (totalLength + 1) / 2 - partition1;

            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == length1) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == length2) ? Integer.MAX_VALUE : nums2[partition2];

            boolean isValidPartition =
                maxLeft1 <= minRight2 &&
                maxLeft2 <= minRight1;

            if (isValidPartition) {
                boolean isTotalLengthOdd = (totalLength & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeft1, maxLeft2);
                }

                int maxOfLeft = Math.max(maxLeft1, maxLeft2);
                int minOfRight = Math.min(minRight1, minRight2);
                return (maxOfLeft + minOfRight) / 2.0;
            }

            if (maxLeft1 > minRight2) {
                high = partition1 - 1;
            } else {
                low = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}